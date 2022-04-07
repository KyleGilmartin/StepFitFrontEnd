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
import com.example.stepfitfrontend.data.Repository
import com.example.stepfitfrontend.ui.screens.MainScreen
import com.example.stepfitfrontend.ui.screens.SignUp
import com.example.stepfitfrontend.ui.theme.StepFitFrontEndTheme
import com.example.stepfitfrontend.ui.viewModel.MainViewModel
import com.example.stepfitfrontend.ui.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        Repository(Api.authService)
    }

    private val viewModel:MainViewModel by viewModels {
        MainViewModelFactory(repository = repository)
    }

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

@Composable
fun Authentication(viewModel: MainViewModel){
    val navHostController = rememberNavController()
   MainScreen(navHostController = navHostController, viewModel = viewModel)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StepFitFrontEndTheme {
        Authentication(viewModel())
    }
}