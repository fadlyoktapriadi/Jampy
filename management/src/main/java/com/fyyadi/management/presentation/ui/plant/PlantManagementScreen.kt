package com.fyyadi.management.presentation.ui.plant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.common.ResultState
import com.fyyadi.core_presentation.R
import com.fyyadi.domain.model.Plant
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans


@Composable
fun PlantManagementScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit = {}
) {
    val viewModel: PlantManagementViewModel = hiltViewModel()
    val plantsState by viewModel.plantsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllPlants()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {

        when (plantsState) {
            is ResultState.Loading -> {
                Column(
                    Modifier
                        .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(6) {  }
                }
            }

            is ResultState.Success -> {
                val plants = (plantsState as ResultState.Success<List<Plant>>).data
                if (plants.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tidak ada data tanaman herbal",
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
//
            is ResultState.Error -> {
                val msg = (plantsState as ResultState.Error).message ?: "Error"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: $msg", color = Color.Red)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.getAllPlants() }) {
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

@Composable
fun PlantItemSearchCard(
    plant: Plant,
    onPlantClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable(
                onClick = { onPlantClick(plant.idPlant) }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = plant.imagePlant,
                contentDescription = plant.plantName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = plant.plantName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = plant.plantSpecies,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = plant.plantDescription,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    lineHeight = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

