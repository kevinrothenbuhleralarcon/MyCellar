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
import ch.kra.mycellar.core.Route
import ch.kra.mycellar.core.Route.WINE_DETAIL_SCREEN
import ch.kra.mycellar.core.Route.WINE_LIST_SCREEN
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
                    startDestination = WINE_LIST_SCREEN
                ) {
                    composable(route =  WINE_LIST_SCREEN) {
                        WineListScreen(navigate = { navController.navigate(it.route) })
                    }
                    composable(route = "$WINE_DETAIL_SCREEN?${Route.Arguments.WINE_ID}={${Route.Arguments.WINE_ID}}",
                        arguments = listOf(
                            navArgument(Route.Arguments.WINE_ID) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val wineId = remember {
                            it.arguments?.getInt(Route.Arguments.WINE_ID)
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