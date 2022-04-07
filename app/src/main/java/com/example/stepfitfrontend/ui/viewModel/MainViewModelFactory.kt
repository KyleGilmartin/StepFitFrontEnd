package com.example.stepfitfrontend.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stepfitfrontend.data.Repository

class MainViewModelFactory(private val repository:Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository = repository) as T
        }else{
            throw ClassNotFoundException()
        }
    }
}