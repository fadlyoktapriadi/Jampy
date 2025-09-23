package com.fyyadi.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.domain.model.PlantLabels
import com.fyyadi.domain.repository.PlantClassificationRepository
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.get
import kotlin.coroutines.resume
import kotlin.run
import kotlin.text.get

@Singleton
class PlantClassificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PlantClassificationRepository {

    companion object {
        private const val MODEL_NAME = "jampy_model"
        private const val INPUT_SIZE = 224
        private const val CONFIDENCE_THRESHOLD = 0.3f
    }

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

    private fun getModelOutputShape(): Int {
        val currentInterpreter = interpreter ?: return 0
        val outputTensor = currentInterpreter.getOutputTensor(0)
        return outputTensor.shape()[1] // This will tell you the actual number of classes
    }

    override suspend fun classifyPlant(bitmap: Bitmap): Result<List<PlantLabel>> =
        withContext(Dispatchers.Default) {
            return@withContext try {
                val currentInterpreter = interpreter
                    ?: return@withContext Result.failure(Exception("Model not loaded"))

                // Preprocess image
                val tensorImage = TensorImage.fromBitmap(bitmap)
                val processedImage = imageProcessor.process(tensorImage)

                // Prepare input and output (should be 10 classes based on your metadata)
                val inputBuffer = processedImage.buffer
                val outputArray = Array(1) { FloatArray(10) } // Fixed size based on metadata

                // Run inference
                currentInterpreter.run(inputBuffer, outputArray)

                // Process results using the correct label mapping
                val predictions = outputArray[0]
                val results = mutableListOf<PlantLabel>()

                predictions.forEachIndexed { index, confidence ->
                    if (confidence > CONFIDENCE_THRESHOLD && index < PlantLabels.LABELS.size) {
                        val label = PlantLabels.LABELS[index] // Use index to get correct label
                        val displayName = PlantLabels.DISPLAY_NAMES[label] ?: label
                        results.add(PlantLabel(label, displayName, confidence))
                    }
                }

                // Sort by confidence descending
                results.sortByDescending { it.confidence }

                Log.e("PlantClassification", "Classification results: $results")
                Result.success(results)
            } catch (e: Exception) {
                Log.e("PlantClassification", "Classification error", e)
                Result.failure(e)
            }
        }


    override fun isModelReady(): Boolean = interpreter != null

}