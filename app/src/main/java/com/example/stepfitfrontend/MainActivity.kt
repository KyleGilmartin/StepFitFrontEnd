package com.example.stepfitfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.stepfitfrontend.data.Api
import com.example.stepfitfrontend.data.PreferenceStore
import com.example.stepfitfrontend.data.Repository
import com.example.stepfitfrontend.ui.screens.MainScreen
import com.example.stepfitfrontend.ui.screens.SignUp
import com.example.stepfitfrontend.ui.theme.StepFitFrontEndTheme
import com.example.stepfitfrontend.ui.viewModel.MainViewModel
import com.example.stepfitfrontend.ui.viewModel.MainViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainActivity : ComponentActivity() {

    private val preferenceStore by lazy {
        PreferenceStore(this)
    }

    private val repository by lazy {
        Repository(Api.authService, preferenceStore = preferenceStore)
    }

    private val viewModel:MainViewModel by viewModels {
        MainViewModelFactory(repository = repository)
    }
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepFitFrontEndTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Authentication(viewModel = viewModel)
                }
            }
        }
    }
}
@ExperimentalPermissionsApi
@Composable
fun Authentication(viewModel: MainViewModel){
    val navHostController = rememberNavController()
   MainScreen(navHostController = navHostController, viewModel = viewModel)
}

@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StepFitFrontEndTheme {
        Authentication(viewModel())
    }
}