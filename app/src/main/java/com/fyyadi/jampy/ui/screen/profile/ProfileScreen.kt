package com.fyyadi.jampy.ui.screen.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profileState by viewModel.profileUserState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
                contentDescription = "Jampy Logo",
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    when (profileState) {
                        is ResultState.Loading -> {
                            Column(
                                Modifier
                                    .padding(bottom = 6.dp, start = 24.dp, end = 24.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
//                    repeat(6) { ShimmerPlantCard(search = true) }
                            }
                        }

                        is ResultState.Success -> {
                            val profile = (profileState as ResultState.Success<com.fyyadi.domain.model.UserProfile?>).data
                            AsyncImage(
                                model = profile?.photoProfile,
                                contentDescription = profile?.photoProfile,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = profile?.userFullName ?: "No Name",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryGreen,
                                fontFamily = RethinkSans,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = profile?.userEmail ?: "No Email",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = PrimaryGreen,
                                fontFamily = RethinkSans,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { /* TODO: Implement logout functionality */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text(text = stringResource(R.string.logout), fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White, fontFamily = RethinkSans
                                )
                            }

                        }

                        is ResultState.Error -> {
                            val message = (profileState as ResultState.Error).message ?: "Unknown Error"
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = message,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = RethinkSans
                                )
                            }

                        }

                        ResultState.Idle -> {

                        }
                    }


                }
            }
        }


    }
}
