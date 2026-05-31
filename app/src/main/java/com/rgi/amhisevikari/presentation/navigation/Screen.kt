package com.rgi.amhisevikari.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Main : Screen("main_screen")
    object SubList : Screen("sub_list_screen/{categoryId}") {
        fun createRoute(categoryId: String) = "sub_list_screen/$categoryId"
    }
    object Lyrics : Screen("lyrics_screen/{bhajanId}") {
        fun createRoute(bhajanId: String) = "lyrics_screen/$bhajanId"
    }
}
