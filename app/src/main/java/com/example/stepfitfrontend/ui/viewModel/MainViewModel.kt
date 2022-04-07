package com.example.stepfitfrontend.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepfitfrontend.data.RegisterUser
import com.example.stepfitfrontend.data.Repository
import com.example.stepfitfrontend.data.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository):ViewModel() {
    private val _registerRequestState = MutableStateFlow<Resource<Nothing>?>(null)
    val registerRequest: StateFlow<Resource<Nothing>?>
    get() = _registerRequestState

    val errorHandler = CoroutineExceptionHandler{_,error ->
        _registerRequestState.value = Resource.Error(error.message!!)
    }

    fun registerUser(registerUser: RegisterUser){
        _registerRequestState.value = Resource.Loading(null)
        viewModelScope.launch ( Dispatchers.IO + errorHandler){
            repository.registerUser(registerUser = registerUser)
            _registerRequestState.value = Resource.Success(null)
        }
    }
}