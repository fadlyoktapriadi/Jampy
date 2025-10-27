package com.fyyadi.jampy.ui.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.components.PlantCard
import com.fyyadi.jampy.ui.components.ShimmerPlantCard
import com.fyyadi.jampy.ui.screen.home.components.WeatherCard
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit = {}
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val profileUserState by viewModel.profileUserState.collectAsState()
    val plantHomeState by viewModel.plantHomeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
        viewModel.getPlantHome()
    }

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundGreen)
                .padding(paddingValues)
        ) {
            TopSection(profileUserState)
            BottomContentSheet(
                modifier = Modifier.align(Alignment.BottomCenter),
                plantHomeState,
                onPlantClick = onPlantClick,
            )
        }
    }
}

@Composable
fun TopSection(
    profileUserState: ResultState<UserProfile?>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .background(BackgroundGreen)
    ) {
        Image(
            painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        WeatherCard(profileUserState)
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.illuplant_home),
            contentDescription = "Plant Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
fun BottomContentSheet(
    modifier: Modifier = Modifier,
    plantHomeState: ResultState<List<Plant>>,
    onPlantClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.46f),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            PopularPlantsSection(plantHomeState, onPlantClick)
        }
    }
}

@Composable
fun PopularPlantsSection(
    plantHomeState: ResultState<List<Plant>>,
    onPlantClick: (Int) -> Unit = {}
) {

    val viewModel: HomeViewModel = hiltViewModel()

    Column {
        Text(
            text = stringResource(R.string.plant_populer),
            fontSize = 18.sp,
            fontFamily = RethinkSans,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (plantHomeState) {
            is ResultState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp
                    )
                ) {
                    repeat(4){
                        ShimmerPlantCard()
                    }
                }
            }

            is ResultState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(plantHomeState.data) { plant ->
                        PlantCard(
                            plant = plant,
                            onClick = { onPlantClick(plant.idPlant) })
                    }
                }
            }

            is ResultState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.failed_to_load_data),
                            fontFamily = RethinkSans,
                            color = Color.Red
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.getPlantHome() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            ResultState.Idle -> return
        }
    }
}