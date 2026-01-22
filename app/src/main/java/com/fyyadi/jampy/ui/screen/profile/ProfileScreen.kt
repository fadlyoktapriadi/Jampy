package com.fyyadi.jampy.ui.screen.profile

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.jampy.R
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.Black600
import com.fyyadi.theme.Green600
import com.fyyadi.theme.OrangePrimary
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RedPrimary
import com.fyyadi.theme.RethinkSans
import com.fyyadi.theme.whiteBackground

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onLoggedOut: () -> Unit,
    onPlantManagementNavigate: () -> Unit,
    onUsersManagementNavigate: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profileState by viewModel.profileUserState.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()

    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    LaunchedEffect(logoutState) {
        when (logoutState) {
            is ResultState.Success -> {
                onLoggedOut()
            }

            else -> {}
        }
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
                text = stringResource(R.string.profile),
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
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(vertical = 16.dp, horizontal = 24.dp)
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
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when (profileState) {
                        is ResultState.Loading -> {
                        }

                        is ResultState.Success -> {
                            val profile = (profileState as ResultState.Success<UserProfile?>).data
                            AsyncImage(
                                model = profile?.photoProfile,
                                contentDescription = profile?.photoProfile,
                                modifier = Modifier
                                    .size(62.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = profile?.userFullName ?: "No Name",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = RethinkSans,
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = profile?.userEmail ?: "No Email",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = RethinkSans,
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = profile?.role ?: "No Role",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = OrangePrimary,
                                fontFamily = RethinkSans,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            if (profile?.role != "User") {
                                Button(
                                    onClick = {
                                        onUsersManagementNavigate()
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Green600,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.users_management),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = RethinkSans
                                    )
                                }
                                Button(
                                    onClick = {
                                        onPlantManagementNavigate()
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Green600,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.plants_management),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = RethinkSans
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    viewModel.logout()
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RedPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.logout),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = RethinkSans
                                )
                            }

                        }

                        is ResultState.Error -> {
                            val message =
                                (profileState as ResultState.Error).message ?: "Unknown Error"
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
                                    fontFamily = RethinkSans,
                                    textAlign = TextAlign.Center
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
