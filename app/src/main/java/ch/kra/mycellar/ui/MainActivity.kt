package ch.kra.mycellar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.kra.mycellar.cellar.presentation.wine_add_edit.composable.WineDetailScreen
import ch.kra.mycellar.cellar.presentation.wine_list.composable.WineListScreen
import ch.kra.mycellar.ui.theme.MyCellarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCellarTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "wine_list_screen"
                ) {
                    composable("wine_list_screen") {
                        WineListScreen(navigate = { navController.navigate("wine_detail_screen/$it") })
                    }
                    composable("wine_detail_screen/{wineId}",
                        arguments = listOf(
                            navArgument("wineId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val wineId = remember {
                            it.arguments?.getInt("wineId")
                        }
                        
                        WineDetailScreen(wineId = wineId!!) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}