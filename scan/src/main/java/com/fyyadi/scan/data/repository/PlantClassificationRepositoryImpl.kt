package com.fyyadi.scan.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.fyyadi.data.mapper.toPlant
import com.fyyadi.data.source.network.dto.PlantDto
import com.fyyadi.domain.model.Plant
import com.fyyadi.scan.data.mapper.toHistoryScanUpsertDto
import com.fyyadi.scan.domain.model.PlantLabel
import com.fyyadi.scan.domain.model.PlantLabels
import com.fyyadi.scan.domain.model.SaveHistoryScanRequest
import com.fyyadi.scan.domain.repository.PlantClassificationRepository
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Singleton
class PlantClassificationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : PlantClassificationRepository {

    private var interpreter: Interpreter? = null
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(INPUT_SIZE, INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
        .add(NormalizeOp(0.0f, 255.0f))
        .build()

    override suspend fun downloadModel(): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            val conditions = CustomModelDownloadConditions.Builder()
                .requireWifi()
                .build()

            val customModel = suspendCancellableCoroutine<CustomModel> { continuation ->
                FirebaseModelDownloader.getInstance()
                    .getModel(MODEL_NAME, DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                    .addOnSuccessListener { model ->
                        continuation.resume(model)
                    }
                    .addOnFailureListener { exception ->
                        continuation.cancel(exception)
                    }
            }

            val modelFile = customModel.file
            if (modelFile != null) {
                interpreter?.close()
                interpreter = Interpreter(modelFile)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Model file is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun classifyPlant(bitmap: Bitmap): Result<List<PlantLabel>> =
        withContext(Dispatchers.Default) {
            return@withContext try {
                val currentInterpreter = interpreter
                    ?: return@withContext Result.failure(Exception("Model not loaded"))

                val tensorImage = TensorImage.fromBitmap(bitmap)
                val processedImage = imageProcessor.process(tensorImage)

                val inputBuffer = processedImage.buffer
                val outputArray = Array(1) { FloatArray(10) }

                currentInterpreter.run(inputBuffer, outputArray)

                val predictions = outputArray[0]
                val results = mutableListOf<PlantLabel>()

                predictions.forEachIndexed { index, confidence ->
                    if (confidence > CONFIDENCE_THRESHOLD && index < PlantLabels.LABELS.size) {
                        val label = PlantLabels.LABELS[index]
                        val displayName = PlantLabels.DISPLAY_NAMES[label] ?: label
                        results.add(PlantLabel(label, displayName, confidence))
                    }
                }

                results.sortByDescending { it.confidence }

                Log.e("PlantClassification", "Classification results: $results")
                Result.success(results)
            } catch (e: Exception) {
                Log.e("PlantClassification", "Classification error", e)
                Result.failure(e)
            }
        }


    override fun isModelReady(): Boolean = interpreter != null

    override fun getDetailResultClassify(plantName: String): Flow<Result<Plant>> {
        return flow {
            val result = runCatching {
                val response = postgrest.from("herb_plants")
                    .select {
                        filter {
                            eq("plant_name", plantName)
                        }
                        limit(1)
                    }
                    .decodeList<PlantDto>()
                response.first().toPlant()
            }
            emit(result)
        }
    }

    override fun saveHistoryScan(
        saveHistoryScanRequest: SaveHistoryScanRequest
    ): Flow<Result<Unit>> = flow {
        val result = runCatching {
            postgrest.from("history_scan").insert(
                value = buildJsonObject {
                    put("user_email", saveHistoryScanRequest.userEmail)
                    put("plant_id", saveHistoryScanRequest.plantId)
                    put("accuracy", saveHistoryScanRequest.accuracy)
                }
            )
            Unit
        }
        emit(result)
    }

    companion object {
        private const val MODEL_NAME = "jampy_model"
        private const val INPUT_SIZE = 224
        private const val CONFIDENCE_THRESHOLD = 0.3f
    }

}