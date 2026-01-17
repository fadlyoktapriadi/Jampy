package com.fyyadi.scan.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.scan.R
import com.fyyadi.scan.domain.model.PlantLabel
import com.fyyadi.scan.presentation.utils.MediaStoreUtils
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.net.toUri
import com.fyyadi.common.ResultState
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.theme.Slate200
import com.fyyadi.theme.SlatePrimary
import com.fyyadi.theme.SlateSecondary
import com.fyyadi.theme.whiteBackground

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    capturedImageUri: String? = null,
    onCameraClick: (
        onResult: (Uri) -> Unit,
        onCancel: () -> Unit
    ) -> Unit,
    onResultClassification: (List<PlantLabel>, String, String) -> Unit
) {
    val viewModel: ScanViewModel = hiltViewModel()

    val selectedImage by viewModel.selectedImage.collectAsState()
    val classificationResult by viewModel.classificationResults.collectAsState()
    val profileState by viewModel.profileUserState.collectAsState()

    var showCamera by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val userEmail = when (profileState) {
        is ResultState.Success -> (profileState as ResultState.Success<UserProfile?>).data?.userEmail
            ?: ""

        else -> ""
    }

    val cropLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
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
            val uri = capturedImageUri.toUri()
            startCrop(
                context = context,
                source = uri,
                onLaunch = { cropLauncher.launch(it) },
                createDest = {
                    MediaStoreUtils.createInternalImageUri(
                        context,
                        "IMG_CROP_${System.currentTimeMillis()}"
                    )
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(whiteBackground)

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
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showCamera = true },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Slate200)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Kamera",
                        fontSize = 14.sp,
                        color = PrimaryGreen,
                        fontFamily = RethinkSans
                    )
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Slate200)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = "Gallery",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Galeri",
                        fontSize = 14.sp,
                        color = PrimaryGreen,
                        fontFamily = RethinkSans
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .clickable {
                    selectedImage?.let { uri ->
                        coroutineScope.launch {
                            val bitmap = withContext(Dispatchers.IO) {
                                try {
                                    val source =
                                        ImageDecoder.createSource(context.contentResolver, uri)
                                    ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                                        decoder.isMutableRequired = true
                                    }
                                } catch (_: Exception) {
                                    null
                                }
                            }
                            if (bitmap != null) {
                                viewModel.classifyImage(bitmap)
                            }
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
                    text = "Processing Image",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        classificationResult.let { result ->
            Log.e("RESULT", result.toString())
            when (result) {
                is ResultState.Idle -> {
                    // no-op
                }

                is ResultState.Loading -> {
                    Text(
                        text = "Loading...",
                        fontSize = 14.sp,
                        color = PrimaryGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                is ResultState.Success -> {
                    val data = result.data
                    if (data.isNotEmpty()) {
                        onResultClassification(data, selectedImage.toString(), userEmail)
                        viewModel.setImage(null)
                    } else {
                        Text(
                            text = "No plants recognized. Please try another image.",
                            fontSize = 14.sp,
                            color = PrimaryGreen,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is ResultState.Error -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Log.e("ScanScreen", "Error: ${result.message}")
                        Text(
                            text = "Error: ${result.message ?: "Unknown error"}",
                            fontSize = 14.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun startCrop(
    context: Context,
    source: Uri,
    onLaunch: (Intent) -> Unit,
    createDest: () -> Uri
) {
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