package com.fyyadi.jampy.ui.screen.search

import androidx.compose.runtime.getValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.domain.model.Plant
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.components.PlantItemSearchCard
import com.fyyadi.jampy.ui.components.ShimmerPlantCard
import com.fyyadi.jampy.ui.theme.BackgroundGreen
import com.fyyadi.jampy.ui.theme.PrimaryGreen

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchPlantState by viewModel.searchPlantState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPlants()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.jampy),
            contentDescription = "Jampy Logo",
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            placeholder = { Text("Cari tanaman...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (searchQuery.isNotEmpty()) {
            Text(
                text = "Search: $searchQuery",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

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
                            PlantItemSearchCard(plant = plant)
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
