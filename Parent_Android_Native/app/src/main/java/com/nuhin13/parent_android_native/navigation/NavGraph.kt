package com.nuhin13.parent_android_native.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants
import com.nuhin13.parent_android_native.flutter_communication.FlutterUtil
import com.nuhin13.parent_android_native.ui.screens.AfterFlutterModuleScreen
import com.nuhin13.parent_android_native.ui.screens.HomeScreen

@Composable
fun SetupNavGraph(windowSize: WindowSize, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.AfterFlutter.route) {
            AfterFlutterModuleScreen(navController = navController)
        }
    }
}