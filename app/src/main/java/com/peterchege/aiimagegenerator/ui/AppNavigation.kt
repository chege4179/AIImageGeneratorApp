/*
 * Copyright 2023 AI Image Generator App Peter Chege Mwangi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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