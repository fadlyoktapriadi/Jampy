package com.fyyadi.management.presentation.ui.plant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.common.ResultState
import com.fyyadi.components.DialogPopUp
import com.fyyadi.core_presentation.R
import com.fyyadi.domain.model.Plant
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.Green100
import com.fyyadi.theme.Green600
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.whiteBackground

@Composable
fun EditPlantScreen(
    modifier: Modifier = Modifier,
    plantId: Int,
    onBackClick: () -> Unit = {}
) {
    val viewModel: PlantManagementViewModel = hiltViewModel()
    val plantDetailState by viewModel.plantDetailState.collectAsState()
    val updatePlantState by viewModel.updatePlantState.collectAsState()

    val focusManager = LocalFocusManager.current
    var showSuccessDialog by remember { mutableStateOf(false) }

    var plantName by remember { mutableStateOf("") }
    var scientificName by remember { mutableStateOf("") }
    var descriptionPlant by remember { mutableStateOf("") }
    var benefits by remember { mutableStateOf("") }
    var processingMethod by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getDetailPlant(plantId)
    }

    LaunchedEffect(updatePlantState) {
        if (updatePlantState is ResultState.Success) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        DialogPopUp(
            title = stringResource(com.fyyadi.management.R.string.success_title_pop_up),
            imageRes = R.drawable.illustration_success,
            description =  stringResource(com.fyyadi.management.R.string.success_title_pop_up),
            onDismissRequest = {
                showSuccessDialog = false
                onBackClick()
            },
            onCloseClick = {
                showSuccessDialog = false
                onBackClick()
            }
        )
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
                        text = state.message ?: "Gagal ambil data",
                        color = Color.Red,
                        fontFamily = RethinkSans
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.getDetailPlant(plantId) }) {
                        Text("Retry")
                    }
                }
            }
        }

        is ResultState.Success -> {
            val plant = state.data
            plantName = plant?.plantName ?: ""
            scientificName = plant?.plantSpecies ?: ""
            descriptionPlant = plant?.plantDescription ?: ""
            benefits = plant?.healthBenefits ?: ""
            processingMethod = plant?.processingMethod ?: ""
            imageUrl = plant?.imagePlant ?: ""

            Scaffold(
                modifier = modifier.fillMaxSize(),
            ) { paddingValues ->

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(whiteBackground)
                        .verticalScroll(rememberScrollState())
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
                            text = stringResource(com.fyyadi.management.R.string.edit_plant_herb),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen,
                            fontFamily = RethinkSans,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = plantName,
                        onValueChange = { plantName = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.name_herb_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = scientificName,
                        onValueChange = { scientificName = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.scientific_name_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descriptionPlant,
                        onValueChange = { descriptionPlant = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.description_herb_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        minLines = 4,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = benefits,
                        onValueChange = { benefits = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.benefits_herb_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        minLines = 2,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )
                    Text(
                        text = stringResource(com.fyyadi.management.R.string.small_desc_fill_benefits),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = RethinkSans,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = processingMethod,
                        onValueChange = { processingMethod = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.processing_method_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        minLines = 2,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = {
                            Text(
                                text = stringResource(com.fyyadi.management.R.string.image_url_label),
                                fontFamily = RethinkSans
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = PrimaryGreen,
                            focusedLabelColor = PrimaryGreen,
                            unfocusedLabelColor = PrimaryGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = RethinkSans,
                            fontSize = 16.sp
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.updatePlant(
                                Plant(
                                    idPlant = plantId,
                                    plantName = plantName,
                                    plantSpecies = scientificName,
                                    plantDescription = descriptionPlant,
                                    healthBenefits = benefits,
                                    processingMethod = processingMethod,
                                    imagePlant = imageUrl
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green600
                        )
                    ) {
                        Text(
                            text = stringResource(com.fyyadi.management.R.string.save_button),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = RethinkSans,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        ResultState.Idle -> { /* Do nothing */
        }
    }
}
