package com.example.stepfitfrontend.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stepfitfrontend.data.RegisterUser
import com.example.stepfitfrontend.data.Resource
import com.example.stepfitfrontend.ui.viewModel.MainViewModel

@Composable
fun SignUp(viewModel: MainViewModel) {

    val registerState = viewModel.registerRequest.collectAsState().value
    val content = LocalContext.current

    val toggleState = remember{
        mutableStateOf(false)
    }
    val passwordState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Email text field
        OutlinedTextField(value =emailState.value , onValueChange = {
            emailState.value = it
        }, modifier = Modifier.fillMaxWidth(),
            label = {Text("Email Address")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        //Password text field
        OutlinedTextField(value =passwordState.value , onValueChange = {
            passwordState.value = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
            label = {Text("Password")},
            trailingIcon = {
                IconButton(onClick = {
                    toggleState.value = !toggleState.value
                }) {
                    Icon(
                        imageVector = if(toggleState.value) Icons.Default.Visibility else
                            Icons.Default.VisibilityOff,
                        contentDescription = ""
                    )
                }
            },
            visualTransformation = if (toggleState.value) VisualTransformation.None else
                PasswordVisualTransformation()
        )


        Button(onClick = {
                         val registerUser = RegisterUser(Email = emailState.value,
                         Password = passwordState.value,
                         Password_confirmation = passwordState.value)
            viewModel.registerUser(registerUser = registerUser)

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign Up", modifier = Modifier.padding(vertical = 8.dp))
        }
        Divider(modifier = Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center) {
            Text(text = "Already have an account?")
            Text(text = "Login",fontWeight = FontWeight.Bold)
        }

        when(registerState){
            is Resource.Loading ->{
                Toast.makeText(content,"Registration in progress",Toast.LENGTH_LONG)
                    .show()
            }
            is Resource.Success ->{
                Toast.makeText(content,"User Successfully Registered",Toast.LENGTH_LONG)
                    .show()
            }
            is Resource.Error ->{
                Toast.makeText(content,"Failed",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview(){
    SignUp(viewModel())
}