package com.fyyadi.jampy.ui.screen.activity

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.fyyadi.components.MarkdownText
import com.fyyadi.domain.model.Plant
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.screen.detail.DetailPlantViewModel
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.Black600
import com.fyyadi.theme.Green100
import com.fyyadi.theme.Green600
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.SecondaryGreen
import com.fyyadi.theme.whiteBackground

@Composable
fun DetailActivityHistoryScanScreen(
    modifier: Modifier = Modifier,
    idPlant: Int,
    imageResultUri: String,
    accuracy: String,
    onBackClick: () -> Unit = {},
) {
    val viewModel: DetailPlantViewModel = hiltViewModel()
    val plantDetailState by viewModel.plantDetailState.collectAsState()

    LaunchedEffect(idPlant) {
        viewModel.getDetailPlant(idPlant)
    }

    when (val state = plantDetailState) {
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
        is ResultState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(BackgroundGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.message ?: stringResource(R.string.failed_to_load_data),
                        color = Color.Red,
                        fontFamily = RethinkSans
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.getDetailPlant(idPlant) }) {
                        Text("Retry")
                    }
                }
            }
        }
        is ResultState.Success -> {
            DetailHistoryScanLoaded(
                plant = state.data,
                imageResultUri = imageResultUri,
                accuracy = accuracy,
                onBackClick = onBackClick,
                modifier = modifier
            )
        }

        ResultState.Idle -> { /* Do nothing */ }
    }
}

@Composable
private fun DetailHistoryScanLoaded(
    plant: Plant?,
    imageResultUri: String,
    accuracy: String,
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
            TitleContentHistoryScan(
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
fun TitleContentHistoryScan(plant: Plant?, accuracy: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = plant?.plantName ?: "",
                fontSize = 20.sp,
                fontFamily = RethinkSans,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryGreen
            )
            Text(
                text = stringResource(R.string.accuracy_result, accuracy),
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
                    contentDescription = imageResultUri,
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
                                    text = benefit.trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase()
                                    else it.toString() },
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
                painter = painterResource(com.fyyadi.core_presentation.R.drawable.ic_left_arrow),
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
                text = stringResource(R.string.detail_history_scan),
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