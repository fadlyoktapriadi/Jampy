package com.fyyadi.jampy.ui.screen.activity

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.ui.components.PlantHistoryScanCard
import com.fyyadi.jampy.ui.components.ShimmerPlantCard
import com.fyyadi.scan.domain.model.HistoryScan
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.whiteBackground

@Composable
fun ActivityHistoryScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (Int, String, String) -> Unit,
    onBackPressed: () -> Unit
) {
    val viewModel: ActivityHistoryViewModel = hiltViewModel()
    val activityHistoryState by viewModel.activityHistoryState.collectAsState()

    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        viewModel.getAllHistoryScanPlants()
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
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = stringResource(R.string.activity),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                fontFamily = RethinkSans,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        when (activityHistoryState) {
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
                val plants = (activityHistoryState as ResultState.Success<List<HistoryScan>>).data
                if (plants.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.no_history),
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = RethinkSans,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                            .fillMaxSize(),
                    ) {
                        items(plants) { plant ->
                            PlantHistoryScanCard(history = plant, onPlantClick)
                        }
                    }
                }
            }

            is ResultState.Error -> {
                val message = (activityHistoryState as ResultState.Error).message ?: "Unknown Error"
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
                    Button(onClick = { viewModel.getAllHistoryScanPlants() }) {
                        Text(text = stringResource(R.string.load_bookmark))
                    }
                }
            }
        }
    }
}
