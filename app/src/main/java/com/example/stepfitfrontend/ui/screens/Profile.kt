package com.example.stepfitfrontend.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stepfitfrontend.R
import com.example.stepfitfrontend.data.LoginUserResponse
import com.example.stepfitfrontend.data.Resource
import com.example.stepfitfrontend.ui.viewModel.MainViewModel

@Composable
fun Profile(viewModel: MainViewModel){

    val tokenState = viewModel.userToken.collectAsState().value
    val context = LocalContext.current
    val token = viewModel.prefToken().collectAsState().value
    val email = viewModel.prefEmail().collectAsState().value





    Box(modifier = Modifier.fillMaxSize()) {

        when(tokenState){
            is Resource.Loading ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success ->{
                if (tokenState.data != null){
                    viewModel.saveToken(tokenState.data.access_token)
                    viewModel.saveEmail(tokenState.data.userName)
                    Log.d("Token",tokenState.data.access_token)
                    Log.d("Token",tokenState.data.userName)
                }
            }
            is Resource.Error ->{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }
        }

        if (token.isNotEmpty()){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(4), elevation = 6.dp) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 16.dp)){
                    Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .border(shape = CircleShape, width = 1.dp, color = Color.White)
                            .clip(
                                CircleShape
                            )
                            .background(color = Color.LightGray)
                    )
                    Text(text = email, modifier = Modifier.padding(top = 16.dp))
                    Button(onClick = {}
                        , modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 32.dp)
                            .fillMaxWidth()){
                        Text(text = "Edit Profile")
                    }


                }



            }
        }


        }
        
    }
@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    Profile(viewModel())
}
