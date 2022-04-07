package com.example.stepfitfrontend.data

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("api/Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)
}