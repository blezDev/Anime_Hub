package com.blez.anime_player_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.blez.anime_player_compose.common.navigation.SetupNavGraph
import com.blez.anime_player_compose.common.util.CredManager
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.ui.theme.Anime_player_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Anime_player_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
                    val credManager = CredManager(this)
                    val window = window
                    val actionBar = actionBar
                    val navHostController = rememberNavController()
                    SetupNavGraph(
                        navHostController,
                        startDestination = if (credManager.getToken() == null) {
                            Screen.HomeScreen.route
                        } else {
                            Screen.HomeScreen.route
                        },
                        window,
                        drawerState = drawerState,
                        actionBar
                    )

                }
            }
        }
    }
}

