package com.peterchege.aiimagegenerator.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.aiimagegenerator.ui.screens.HistoryScreen
import com.peterchege.aiimagegenerator.ui.screens.HomeScreen
import com.peterchege.aiimagegenerator.util.Screens


@Composable
fun AppNavigation (
    navController:NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.HOME_SCREEN){
        composable(
            route = Screens.HOME_SCREEN
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screens.HISTORY_SCREEN
        ){
            HistoryScreen(navController = navController)
        }
    }
}