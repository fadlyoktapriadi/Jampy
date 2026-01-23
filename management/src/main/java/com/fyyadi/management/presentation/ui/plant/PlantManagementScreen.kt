package com.fyyadi.management.presentation.ui.plant

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.common.ResultState
import com.fyyadi.components.DialogPopUp
import com.fyyadi.core_presentation.R
import com.fyyadi.domain.model.Plant
import com.fyyadi.theme.Green100
import com.fyyadi.theme.Green600
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RedPrimary
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.whiteBackground

@Composable
fun PlantManagementScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onAddNewPlantClick: () -> Unit = {},
    onEditPlantClick: (Int) -> Unit = {}
) {
    val viewModel: PlantManagementViewModel = hiltViewModel()
    val plantsState by viewModel.plantsState.collectAsState()
    val deletePlantState by viewModel.deletePlantState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    var idDelete by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getAllPlants()
    }

    LaunchedEffect(deletePlantState) {
        if (deletePlantState is ResultState.Success) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        DialogPopUp(
            title = stringResource(com.fyyadi.management.R.string.success_title_pop_up),
            imageRes = R.drawable.illustration_success,
            description = stringResource(com.fyyadi.management.R.string.success_description_delete_pop_up),
            onDismissRequest = { showSuccessDialog = false },
            onCloseClick = {
                showSuccessDialog = false
                viewModel.getAllPlants()
            }
        )
    }

    if (showConfirmDeleteDialog) {
        DialogPopUp(
            title = stringResource(com.fyyadi.management.R.string.confirm_pop_up),
            imageRes = R.drawable.illustration_alert,
            description = stringResource(com.fyyadi.management.R.string.description_confirm_pop_up),
            onDismissRequest = { showSuccessDialog = false },
            onCloseClick = {
                showConfirmDeleteDialog = false
            },
            textConfirm = stringResource(com.fyyadi.management.R.string.delete_button),
            onConfirmClick = {
                viewModel.deletePlant(idDelete)
                showConfirmDeleteDialog = false
            },
            colorConfirmButton = RedPrimary
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddNewPlantClick() },
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
                .background(whiteBackground)
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
                Text(
                    text = stringResource(com.fyyadi.management.R.string.manage_data_plant),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen,
                    fontFamily = RethinkSans,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
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
                                text = stringResource(com.fyyadi.management.R.string.no_data_plant),
                                color = PrimaryGreen,
                                fontFamily = RethinkSans,
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
                                ItemPlantManagement(
                                    plant = plant,
                                    onEditPlantClick = {
                                        onEditPlantClick(plant.idPlant)
                                    }, onDeletePlantClick = {
                                        idDelete = plant.idPlant
                                        showConfirmDeleteDialog = true
                                    }
                                )
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
