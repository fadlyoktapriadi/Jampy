package com.fyyadi.scan.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.common.ResultState
import com.fyyadi.components.DialogPopUp
import com.fyyadi.components.MarkdownText
import com.fyyadi.core_presentation.R
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.PlantLabel
import com.fyyadi.theme.Black600
import com.fyyadi.theme.Green100
import com.fyyadi.theme.Green600
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.SecondaryGreen
import com.fyyadi.theme.whiteBackground

@Composable
fun ResultScanScreen(
    modifier: Modifier = Modifier,
    plantResult: PlantLabel,
    imageResultUri: String,
    userEmail: String,
    onBackClick: () -> Unit
) {

    val viewModel: ScanViewModel = hiltViewModel()
    val detailResultClassify by viewModel.detailResultClassify.collectAsState()
    var lowConfidence by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDetailResultClassify(plantResult.displayName)
    }

    LaunchedEffect(plantResult.displayName) {
        plantResult.displayName.let { viewModel.getDetailResultClassify(it) }
    }

    if (lowConfidence) {
        DialogPopUp(
            title = "Hasil Klasifikasi",
            imageRes = R.drawable.illustration_alert,
            description = "Akurasi dibawah 50% kemungkinan sistem sulit mendeteksi foto tanaman herbal",
            onDismissRequest = {
                lowConfidence = false
                onBackClick()
            },
            onCloseClick = {
                lowConfidence = false
                onBackClick()
            },
            modifier = modifier
        )
    }

    when (detailResultClassify) {
        is ResultState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(whiteBackground),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
        }

        is ResultState.Success -> {
            val plant = (detailResultClassify as ResultState.Success<Plant?>).data

            if(plantResult.confidence < 0.5f) lowConfidence = true

            LaunchedEffect(plant?.idPlant) {
                plant?.let {
                    val acc = (plantResult.confidence.times(100)).toInt()
                    viewModel.saveHistoryScan(
                        userEmail = userEmail,
                        plantId = it.idPlant,
                        accuracy = acc,
                    )

                    viewModel.saveHistoryScanLocal(
                        userEmail = userEmail,
                        plantId = it.idPlant,
                        plantNamePredict = it.plantName,
                        imageResultUri = imageResultUri,
                        accuracy = acc
                    )
                }
            }

            DetailPlantResult(
                plant = plant,
                imageResultUri = imageResultUri,
                accuracy = plantResult.confidence,
                onBackClick = onBackClick,
                modifier = modifier
            )
        }

        is ResultState.Error -> {
            val errorMsg = (detailResultClassify as ResultState.Error).message

            DialogPopUp(
                title = "Terjadi Kesalahan",
                imageRes = R.drawable.illustration_error,
                description = errorMsg.toString(),
                onDismissRequest = {
                    onBackClick()
                },
                onCloseClick = {
                    onBackClick()
                },
                modifier = modifier
            )
        }

        ResultState.Idle -> {
            // Initial state, do nothing or show a placeholder
        }
    }
}

@Composable
private fun DetailPlantResult(
    plant: Plant?,
    imageResultUri: String,
    accuracy: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val headerHeight = 300.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val topBarAlpha by animateFloatAsState(
        targetValue = if (scrollState.value > headerHeightPx) 1f else 0f,
        animationSpec = tween(300),
        label = "topBarAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(whiteBackground)
    ) {
        Column {
            HeaderContent(
                imageResultUri = imageResultUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .padding(top = 16.dp, bottom = 8.dp)
            )
            TitleContent(
                plant = plant,
                accuracy = accuracy,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
        BodyContent(
            plant = plant,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            headerHeight = headerHeight + 80.dp
        )
        StaticTopBar(
            onBackClick = onBackClick,
        )
        DynamicTopBar(
            plantName = plant?.plantName ?: "",
            alpha = topBarAlpha
        )
    }
}

@Composable
fun TitleContent(plant: Plant?, accuracy: Float, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = plant?.plantName ?: "",
                fontSize = 20.sp,
                fontFamily = RethinkSans,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryGreen
            )
            val accuracyPercent = (accuracy * 100).toInt()
            Text(
                text = "Akurasi: ${accuracyPercent}%",
                fontSize = 16.sp,
                fontFamily = RethinkSans,
                fontWeight = FontWeight.ExtraBold,
                color = Green600
            )
        }

        Text(
            text = plant?.plantSpecies ?: "",
            fontSize = 14.sp,
            fontFamily = RethinkSans,
            color = SecondaryGreen,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun HeaderContent(imageResultUri: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(top = 86.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
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
                AsyncImage(
                    model = imageResultUri,
                    contentDescription = "Result Image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BodyContent(plant: Plant?, modifier: Modifier = Modifier, headerHeight: Dp) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(headerHeight))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = screenHeight),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.description),
                    fontFamily = RethinkSans,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = plant?.plantDescription ?: "",
                    fontFamily = RethinkSans,
                    color = Black600,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.benefits),
                    fontFamily = RethinkSans,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plant?.healthBenefits?.split(";")?.forEach { benefit ->
                        if (benefit.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = PrimaryGreen,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = benefit.trim().replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase()
                                        else it.toString()
                                    },
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontFamily = RethinkSans
                                )
                            }
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.how_to_usage),
                    fontFamily = RethinkSans,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                MarkdownText(
                    markdown = plant?.processingMethod ?: "",
                    modifier = Modifier
                        .offset(x = (-10).dp)
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun StaticTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Green100)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = "Back",
                tint = Green600,
                modifier = Modifier.size(24.dp)
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.result_scan),
                fontFamily = RethinkSans,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryGreen
            )
        }
    }
}

@Composable
fun DynamicTopBar(
    plantName: String,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Green600.copy(alpha = alpha))
            .padding(horizontal = 14.dp, vertical = 24.dp)
            .graphicsLayer { this.alpha = alpha },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = plantName,
            fontSize = 18.sp,
            fontFamily = RethinkSans,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}