package com.fyyadi.management.presentation.ui.plant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.common.ResultState
import com.fyyadi.core_presentation.R
import com.fyyadi.domain.model.Plant
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.Green500
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans

@Composable
fun PlantManagementScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val viewModel: PlantManagementViewModel = hiltViewModel()
    val plantsState by viewModel.plantsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllPlants()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: handle add plant */ },
                containerColor = OrangePrimary,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(com.fyyadi.management.R.drawable.ic_add),
                    contentDescription = "Add Plant",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGreen)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Green500.copy(alpha = 0.8f))
                        .clickable(onClick = onBackClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_left_arrow),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Kelola Data Tanaman Herbal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen,
                    fontFamily = RethinkSans,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (plantsState) {
                is ResultState.Loading -> {
                    Column(
                        Modifier
                            .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        repeat(6) { }
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
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 45.dp)
                        ) {
                            items(plants) { plant ->
                                ItemPlantManagement(plant = plant, onPlantClick)
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
}
