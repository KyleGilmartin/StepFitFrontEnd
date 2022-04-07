package com.example.stepfitfrontend.data

data class RegisterUser(
    val Email:String,
    val Password:String,
    val Password_confirmation:String

)

data class LoginUserResponse(
    val access_token :String="",
    val token_type:String="",
    val expires_in:String="",
    val userName:String=""
)
