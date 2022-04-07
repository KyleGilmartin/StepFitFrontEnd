package com.example.stepfitfrontend.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stepfitfrontend.ui.viewModel.MainViewModel

@Composable
fun MainScreen(navHostController: NavHostController,viewModel: MainViewModel){
    NavHost(navController = navHostController, startDestination = "signup"){
        composable("signup"){
            SignUp(viewModel)
        }
    }
}