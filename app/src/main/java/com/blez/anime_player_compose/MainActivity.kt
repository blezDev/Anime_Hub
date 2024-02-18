package com.blez.anime_player_compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blez.anime_player_compose.common.navigation.SetupNavGraph
import com.blez.anime_player_compose.common.util.BottomNavItem
import com.blez.anime_player_compose.common.util.CredManager
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_search.presentation.SearchScreen
import com.blez.anime_player_compose.ui.theme.Anime_player_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Anime_player_composeTheme {
                // A surface container using the 'background' color from the theme

                val bottomNavigationItems = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.List,
                    BottomNavItem.Profile,

                    )




                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                    bottomNavigationItems.forEach {
                        NavigationDrawerItem(
                            label = { it.label },
                            selected = it.active ,
                            onClick = {
                                it.active = !it.active
                            })
                    }
                    }

                    ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),

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
}

