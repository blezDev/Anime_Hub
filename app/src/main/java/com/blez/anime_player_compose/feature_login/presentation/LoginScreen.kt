package com.blez.anime_player_compose.feature_login.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Constants
import com.blez.anime_player_compose.common.util.CredManager
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.core.component.GoogleButton
import com.blez.anime_player_compose.feature_dashboard.data.remote.GoogleOuthAPI
import com.stevdzasan.onetap.OneTapGoogleButton
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun LoginScreen(navController: NavHostController) {
    var authenticated by remember {
        mutableStateOf(false)
    }
    var errorState by remember {
        mutableStateOf(false)
    }
    val credManager = CredManager(LocalContext.current)
    val scope = rememberCoroutineScope()
    val oneTapSignInState = rememberOneTapSignInState()
    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = Constants.clientId,
        onTokenIdReceived = { tokenId ->
            authenticated = true
            credManager.saveToken(tokenId)
            val google = Retrofit.Builder()
                .baseUrl("https://oauth2.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleOuthAPI::class.java)
            scope.launch {
                val result = google.getGoogleProfile(tokenId)
                    .body()
                credManager.saveProfilePic(result?.picture.toString())
                credManager.saveGivenName(result?.given_name.toString())
                credManager.saveFamilyName(result?.family_name.toString())
                credManager.saveName(result?.name.toString())
            }
            Log.e("TAG", tokenId)

        },
        onDialogDismissed = {
            Log.e("DIS", it)
        },
        rememberAccount = false
    )

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.login_bg),
        contentDescription = "Background Image"
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = FontFamily.SansSerif,
            fontSize = 40.sp,
            color = Color(red = 99, green = 89, blue = 133),
            fontWeight = FontWeight.Bold
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp), contentAlignment = Alignment.BottomCenter
    ) {
        GoogleButton(enabled = !oneTapSignInState.opened, onClicked = {
            oneTapSignInState.open()
        })
    }
    if (authenticated) {
        val context = LocalContext.current
        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()

        LaunchedEffect(key1 = authenticated) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }

}

