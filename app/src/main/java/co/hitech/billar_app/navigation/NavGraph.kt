package co.hitech.billar_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.hitech.billar_app.presentation.player.PlayerScreen
import co.hitech.billar_app.presentation.setup.GameSetupScreen

/**
 * Navigation graph for the app
 */
object NavigationRoutes {
    const val GAME_SETUP = "game_setup"
    const val GAME_PLAY = "game_play/{playerCount}/{delayTime}"
    
    fun gamePlayRoute(playerCount: Int, delayTime: Int): String {
        return "game_play/$playerCount/$delayTime"
    }
}

/**
 * Sets up the navigation graph
 */
@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.GAME_SETUP
    ) {
        // Setup screen
        composable(NavigationRoutes.GAME_SETUP) {
            GameSetupScreen(
                onStartGame = { playerCount, delayTime ->
                    navController.navigate(
                        NavigationRoutes.gamePlayRoute(playerCount, delayTime)
                    ) {
                        // Clear the setup screen from back stack
                        popUpTo(NavigationRoutes.GAME_SETUP) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        
        // Game play screen
        composable(
            route = NavigationRoutes.GAME_PLAY,
            arguments = listOf(
                navArgument("playerCount") { 
                    type = NavType.IntType
                },
                navArgument("delayTime") { 
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val playerCount = backStackEntry.arguments?.getInt("playerCount") ?: 2
            val delayTime = backStackEntry.arguments?.getInt("delayTime") ?: 30
            
            PlayerScreen(
                playerCount = playerCount,
                delayTime = delayTime
            )
        }
    }
}
