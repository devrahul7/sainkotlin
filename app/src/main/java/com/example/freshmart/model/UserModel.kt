package com.example.freshmart.model



data class UserModel(
    var userID : String = "",
    var fullName : String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var address : String = ""
)