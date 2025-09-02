package com.fyyadi.jampy.ui.screen.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.domain.model.Plant
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.components.PlantItemSearchCard
import com.fyyadi.jampy.ui.components.ShimmerPlantCard
import com.fyyadi.jampy.ui.theme.BackgroundGreen
import com.fyyadi.jampy.ui.theme.RethinkSans

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit = {}
) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val bookmarkPlantState by viewModel.bookmarkPlantState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllBookmarkPlants()
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

        when (bookmarkPlantState) {
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
                val plants = (bookmarkPlantState as ResultState.Success<List<Plant>>).data
                if (plants.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.no_bookmark), fontSize = 16.sp,
                            color = Color.Black, fontFamily = RethinkSans
                        )
                    }
                    return@Column
                }else {
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
                val message = (bookmarkPlantState as ResultState.Error).message ?: "Unknown Error"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = message, color = Color.Red, fontSize = 16.sp)
                }
            }
            ResultState.Idle -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { viewModel.getAllBookmarkPlants() }) {
                        Text(text = "Load Bookmarks")
                    }
                }
            }
        }
    }
}
