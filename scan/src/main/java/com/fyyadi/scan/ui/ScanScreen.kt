package com.fyyadi.scan.ui

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.scan.R
import com.fyyadi.scan.utils.MediaStoreUtils
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    capturedImageUri: String? = null,
    onCameraClick: (
        onResult: (Uri) -> Unit,
        onCancel: () -> Unit
    ) -> Unit,
    onResultClassification: (List<PlantLabel>, String) -> Unit
) {
    val viewModel: ScanViewModel = hiltViewModel()

    val selectedImage by viewModel.selectedImage.collectAsState()
    val isProcessing by viewModel.isProcessing.collectAsState()
    val classificationResult by viewModel.classificationResults.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    var showCamera by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val cropLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.setProcessing(false)
        if (result.resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            viewModel.setImage(resultUri)
        }
    }

    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            startCrop(
                context = context,
                source = uri,
                onLaunch = { cropLauncher.launch(it) },
                setProcessing = viewModel::setProcessing,
                createDest = {
                    MediaStoreUtils.createInternalImageUri(
                        context,
                        "IMG_CROP_${System.currentTimeMillis()}"
                    )
                }
            )
        }
    }

    if (showCamera) {
        onCameraClick(
            { uri ->
                showCamera = false
                startCrop(
                    context = context,
                    source = uri,
                    onLaunch = { cropLauncher.launch(it) },
                    setProcessing = viewModel::setProcessing,
                    createDest = {
                        MediaStoreUtils.createInternalImageUri(
                            context,
                            "IMG_CROP_${System.currentTimeMillis()}"
                        )
                    }
                )
            },
            { showCamera = false }
        )
        return
    }

    LaunchedEffect(capturedImageUri) {
        if (capturedImageUri != null) {
            val uri = Uri.parse(capturedImageUri)
            startCrop(
                context = context,
                source = uri,
                onLaunch = { cropLauncher.launch(it) },
                setProcessing = viewModel::setProcessing,
                createDest = {
                    MediaStoreUtils.createInternalImageUri(
                        context,
                        "IMG_CROP_${System.currentTimeMillis()}"
                    )
                }
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGreen)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
                contentDescription = "Jampy Logo",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Scan Tanaman Herbal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                fontFamily = RethinkSans,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 32.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImage != null) {
                        AsyncImage(
                            model = selectedImage,
                            contentDescription = "Selected",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.image_placeholder_filled),
                            contentDescription = "Placeholder",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 24.dp)
                .clickable { showCamera = true },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Jampy Logo",
                    modifier = Modifier.size(24.dp)
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Take by Camera",
                        fontSize = 14.sp,
                        color = PrimaryGreen
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                .clickable { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "Gallery",
                    modifier = Modifier.size(24.dp)
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Select from Gallery",
                        fontSize = 14.sp,
                        color = PrimaryGreen
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .clickable {
                    selectedImage?.let { uri ->
                        coroutineScope.launch {
                            processImage(context, uri, viewModel)
                        }
                    }
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = OrangePrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.ic_image_process),
                    contentDescription = "Processing",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Processing Image...",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        classificationResult.let { result ->
            Log.e("RESULT", result.toString())
            if (result.isNotEmpty()) {
                val imageUriString = selectedImage?.toString().orEmpty()
                LaunchedEffect(result) {
                    onResultClassification(result, imageUriString)
                    viewModel.setImage(null)
                }
            }
        }

        error?.let { errorMessage ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Log.e("ScanScreen", "Error: $errorMessage")
                Text(
                    text = "Error: $errorMessage",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

private suspend fun processImage(
    context: android.content.Context,
    uri: Uri,
    viewModel: ScanViewModel
) {
    val bitmap = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                val source = android.graphics.ImageDecoder.createSource(context.contentResolver, uri)
                android.graphics.ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.isMutableRequired = true
                }
            } else {
                @Suppress("DEPRECATION")
                android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
        } catch (_: Exception) {
            null
        }
    }
    if (bitmap != null) {
        viewModel.classifyImage(bitmap)
        viewModel.setProcessing(false)
    }
}

private fun startCrop(
    context: android.content.Context,
    source: Uri,
    onLaunch: (android.content.Intent) -> Unit,
    setProcessing: (Boolean) -> Unit,
    createDest: () -> Uri
) {
    setProcessing(true)
    val destinationUri = createDest()
    val options = UCrop.Options().apply {
        setFreeStyleCropEnabled(true)
        setHideBottomControls(false)
    }
    val intent = UCrop.of(source, destinationUri)
        .withOptions(options)
        .getIntent(context)
    onLaunch(intent)
}