package com.example.stepfitfrontend.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stepfitfrontend.data.LoginUserResponse
import com.example.stepfitfrontend.data.RegisterUser
import com.example.stepfitfrontend.data.Repository
import com.example.stepfitfrontend.data.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository):ViewModel() {
    private val _registerRequestState = MutableStateFlow<Resource<Nothing>?>(null)
    val registerRequest: StateFlow<Resource<Nothing>?>
    get() = _registerRequestState

    private val _userToken = MutableStateFlow<Resource<LoginUserResponse>?>(null)
    val userToken:StateFlow<Resource<LoginUserResponse>?>
    get() = _userToken

    val errorHandler = CoroutineExceptionHandler{_,error ->
        _registerRequestState.value = Resource.Error(error.message!!)
        _userToken.value = Resource.Error(error.message!!)
    }

    fun registerUser(registerUser: RegisterUser){
        _registerRequestState.value = Resource.Loading(null)
        viewModelScope.launch ( Dispatchers.IO + errorHandler){
            repository.registerUser(registerUser = registerUser)
            _registerRequestState.value = Resource.Success(null)
        }
    }

    fun loginUser(username:String,password:String){
        _userToken.value = Resource.Loading(null)
        viewModelScope.launch (Dispatchers.IO + errorHandler){
            val result = repository.loginUser(username = username,
            password = password)
            _userToken.value = Resource.Success(result)
        }
    }

    fun saveToken(token:String){
        viewModelScope.launch {
            delay(500L)
            repository.saveToken(token = token)
        }
    }
    fun saveEmail(email:String){
        viewModelScope.launch {
            delay(500L)
            repository.saveEmail(email = email)
        }
    }

    private val _prefToken = MutableStateFlow("")
    fun prefToken():StateFlow<String>{
        viewModelScope.launch {
            repository.tokenPref.collect {
                _prefToken.value = it
            }
        }
       return  _prefToken
    }

    private val _prefEmail = MutableStateFlow("")
    fun prefEmail():StateFlow<String>{
        viewModelScope.launch {
            repository.emailPref.collect {
                _prefEmail.value = it
            }
        }
        return  _prefEmail
    }
}