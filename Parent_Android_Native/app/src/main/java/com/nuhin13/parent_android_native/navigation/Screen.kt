package com.nuhin13.parent_android_native.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AfterFlutter : Screen("after_flutter")
    object Calculation : Screen("calculation")
}