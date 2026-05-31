package com.rgi.amhisevikari.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.rgi.amhisevikari.presentation.screens.lyrics.LyricsScreen
import com.rgi.amhisevikari.presentation.screens.main.MainScreen
import com.rgi.amhisevikari.presentation.screens.splash.SplashScreen
import com.rgi.amhisevikari.presentation.screens.sublist.SubListScreen

@Composable
fun AmhiSevekariNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToMain = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToSubList = { categoryId ->
                    navController.navigate(Screen.SubList.createRoute(categoryId))
                },
                onNavigateToLyrics = { bhajanId ->
                    navController.navigate(Screen.Lyrics.createRoute(bhajanId))
                }
            )
        }
        
        composable(
            route = Screen.SubList.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            SubListScreen(
                categoryId = categoryId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLyrics = { bhajanId ->
                    navController.navigate(Screen.Lyrics.createRoute(bhajanId))
                }
            )
        }
        
        composable(
            route = Screen.Lyrics.route,
            arguments = listOf(navArgument("bhajanId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bhajanId = backStackEntry.arguments?.getString("bhajanId") ?: ""
            LyricsScreen(
                bhajanId = bhajanId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
