package com.fyyadi.jampy.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.jampy.BuildConfig
import com.fyyadi.jampy.common.ResultState
import com.fyyadi.jampy.R
import com.fyyadi.theme.Amarant
import com.fyyadi.theme.BackgroundGreen
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val loginAuthState by viewModel.loginAuthState.collectAsState()
    val checkUserLoginState by viewModel.checkUserLoginState.collectAsState()
    val addUserState by viewModel.addUserState.collectAsState()

    LaunchedEffect(checkUserLoginState) {
        onCheckUserLoginState(checkUserLoginState, viewModel, context, onLoginSuccess)
    }
    LaunchedEffect(loginAuthState) {
        onLoginAuthState(loginAuthState, viewModel, context)
    }
    LaunchedEffect(addUserState) {
        onAddUserState(addUserState, viewModel, context, onLoginSuccess)
    }

    val credentialManager = remember { CredentialManager.create(context) }
    val googleIdOption: GetGoogleIdOption = remember {
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()
    }
    val request: GetCredentialRequest = remember {
        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    val isLoading = loginAuthState is ResultState.Loading

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = com.fyyadi.core_presentation.R.drawable.jampy),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(100.dp)
            )
            WelcomeHeader()
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(12.dp, vertical = 4.dp))
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            performGoogleSignIn(
                                credentialManager = credentialManager,
                                request = request,
                                context = context,
                                viewModel = viewModel
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PrimaryGreen, shape = ButtonDefaults.shape)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(com.fyyadi.core_presentation.R.drawable.ic_google),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.button_login),
                            fontSize = 14.sp,
                            fontFamily = RethinkSans,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(top = 14.dp)
    ) {
        Text(
            text = stringResource(R.string.welcome_text_nature),
            fontFamily = Amarant,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 30.sp,
            color = PrimaryGreen,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.welcome_text_explore),
            fontFamily = Amarant,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.welcome_text_discover),
            fontFamily = RethinkSans,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

private fun onCheckUserLoginState(
    state: ResultState<Boolean>,
    viewModel: LoginViewModel,
    context: Context,
    onLoginSuccess: () -> Unit
) {
    when (state) {
        is ResultState.Success -> {
            if (state.data) {
                viewModel.saveUserLogin()
                onLoginSuccess()
            } else {
                viewModel.addUser()
            }
        }
        is ResultState.Error -> {
            Toast.makeText(
                context,
                state.message ?: "Failed to check user login",
                Toast.LENGTH_LONG
            ).show()
        }
        else -> Unit
    }
}

private fun onLoginAuthState(
    state: ResultState<*>,
    viewModel: LoginViewModel,
    context: android.content.Context
) {
    when (state) {
        is ResultState.Success -> viewModel.checkUserLogin()
        is ResultState.Error -> {
            Toast.makeText(
                context,
                state.message ?: "Login failed",
                Toast.LENGTH_LONG
            ).show()
        }
        else -> Unit
    }
}

private fun onAddUserState(
    state: ResultState<*>,
    viewModel: LoginViewModel,
    context: Context,
    onLoginSuccess: () -> Unit
) {
    when (state) {
        is ResultState.Success -> {
            viewModel.saveUserLogin()
            onLoginSuccess()
        }
        is ResultState.Error -> {
            Toast.makeText(
                context,
                state.message ?: "Failed to save user",
                Toast.LENGTH_LONG
            ).show()
        }
        else -> Unit
    }
}

private suspend fun performGoogleSignIn(
    credentialManager: CredentialManager,
    request: GetCredentialRequest,
    context: Context,
    viewModel: LoginViewModel
) {
    try {
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        val googleIdCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
        val googleIdToken = googleIdCredential.idToken
        val userProfile = UserProfile(
            idUser = null,
            email = googleIdCredential.id,
            fullName = googleIdCredential.displayName,
            photoProfile = googleIdCredential.profilePictureUri.toString(),
            role = "User"
        )
        viewModel.loginWithGoogle(googleIdToken)
        viewModel.updateUserProfile(userProfile)
    } catch (e: GetCredentialException) {
        Toast.makeText(context, "Sign-in failed: ${e.message}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "An unknown error occurred. ${e.message}", Toast.LENGTH_LONG).show()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomePage() {
    LoginScreen()
}