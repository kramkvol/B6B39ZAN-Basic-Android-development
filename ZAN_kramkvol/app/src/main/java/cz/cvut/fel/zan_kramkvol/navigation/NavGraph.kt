package cz.cvut.fel.zan_kramkvol.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cvut.fel.zan_kramkvol.ui.screen.ImportBookScreen
import cz.cvut.fel.zan_kramkvol.ui.screen.AddBookScreen
import cz.cvut.fel.zan_kramkvol.ui.screen.EditBookScreen
import cz.cvut.fel.zan_kramkvol.ui.screen.MainScreen
import cz.cvut.fel.zan_kramkvol.ui.screen.SettingsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("add") { AddBookScreen(navController) }
        composable("edit/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            bookId?.let {
                EditBookScreen(navController = navController, bookId = it)
            }
        }
        composable("search") { ImportBookScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
