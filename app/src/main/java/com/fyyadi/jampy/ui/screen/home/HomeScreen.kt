package com.fyyadi.jampy.ui.screen.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.fyyadi.jampy.ui.components.PlantItemSearchCard
import com.fyyadi.jampy.ui.components.ShimmerPlantCard
import com.fyyadi.theme.Green400
import com.fyyadi.theme.Green600
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.whiteBackground

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit = {}
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val profileUserState by viewModel.profileUserState.collectAsState()
    val plantHomeState by viewModel.plantHomeState.collectAsState()
    val searchPlantState by viewModel.searchPlantState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
        viewModel.getPlantHome()
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.getPlants()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
    ) {
        Row {
            Image(
                painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(start = 16.dp, top = 24.dp)
                    .size(48.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                placeholder = { Text("Cari tanaman...", color = Color.Gray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.search_grey),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!searchQuery.isNotEmpty()) {
            when (profileUserState) {
                is ResultState.Loading -> {}
                is ResultState.Success -> {
                    val userProfile = (profileUserState as ResultState.Success<UserProfile?>).data
                    Text(
                        text = "Hai, ${userProfile?.userFullName}",
                        fontSize = 24.sp,
                        fontFamily = RethinkSans,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    )
                }

                is ResultState.Error -> {}
                ResultState.Idle -> {}
            }

            CardInformationHome()

            PopularPlantsSection(plantHomeState, onPlantClick)
        } else {
            when (searchPlantState) {
                is ResultState.Loading -> {
                    Column(
                        Modifier
                            .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        repeat(6) { ShimmerPlantCard(search = true) }
                    }
                }

                is ResultState.Success -> {
                    val plants = (searchPlantState as ResultState.Success<List<Plant>>).data
                    if (plants.isEmpty() && searchQuery.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada hasil untuk \"$searchQuery\"",
                                color = PrimaryGreen,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            Modifier
                                .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(plants) { plant ->
                                PlantItemSearchCard(plant = plant, onPlantClick)
                            }
                        }
                    }
                }

                is ResultState.Error -> {
                    val msg = (searchPlantState as ResultState.Error).message ?: "Error"
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: $msg", color = Color.Red)
                            Spacer(Modifier.height(12.dp))
                            Button(onClick = { viewModel.getPlants() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                ResultState.Idle -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryGreen)
                    }
                }
            }
        }

    }

}

@Composable
fun CardInformationHome() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Green400
        ),
        border = BorderStroke(2.dp, PrimaryGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, PrimaryGreen),
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            ) {
                Text(
                    text = "Jampy",
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(3f)) {
                    Text(
                        text = "Cukup satu foto, untuk kenali tanaman herbal dan resepnya",
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 16.sp
                    )
                }

                Image(
                    painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.illustration_plant),
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )
            }
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
            text = stringResource(R.string.list_plant),
            fontSize = 18.sp,
            fontFamily = RethinkSans,
            fontWeight = FontWeight.Bold,
            color = Green600,
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
                    repeat(4) {
                        ShimmerPlantCard()
                    }
                }
            }

            is ResultState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(plantHomeState.data) { plant ->
                        PlantCard(
                            plant = plant,
                            onClick = { onPlantClick(plant.idPlant) }
                        )
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