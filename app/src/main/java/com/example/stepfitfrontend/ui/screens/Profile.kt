package com.example.stepfitfrontend.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stepfitfrontend.R
import com.example.stepfitfrontend.Utils.bitmapToBase64
import com.example.stepfitfrontend.data.LoginUserResponse
import com.example.stepfitfrontend.data.Resource
import com.example.stepfitfrontend.ui.viewModel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlin.contracts.contract


@ExperimentalPermissionsApi
@Composable
fun Profile(viewModel: MainViewModel){

    val tokenState = viewModel.userToken.collectAsState().value
    val context = LocalContext.current
    val token = viewModel.prefToken().collectAsState().value
    val email = viewModel.prefEmail().collectAsState().value


    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri: Uri? ->
        imageUri = uri
    }

    var bitmap:Bitmap? = null
    imageUri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28){
            MediaStore.Images.Media.getBitmap(context.contentResolver,it)
        }else{
            val source = ImageDecoder.createSource(context.contentResolver,it)
            ImageDecoder.decodeBitmap(source)
        }
    }

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
                    bitmap?.let {
                        btm->
                        val imageUrl = btm.bitmapToBase64()
                    }
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
                    Button(onClick = {
                                     when{
                                         permissionState.hasPermission ->{
                                             launcher.launch("image/*")
                                         }
                                         permissionState.shouldShowRationale ->{
                                             Toast.makeText(context,"Give permission to access your images",Toast.LENGTH_SHORT).show()
                                         }else ->{
                                             permissionState.launchPermissionRequest()
                                         }
                                     }
                    }
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
@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    Profile(viewModel())
}
