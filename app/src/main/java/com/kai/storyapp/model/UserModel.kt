package com.kai.storyapp.model


data class UserModel (
    var name: String,
    var email: String,
    var password: String,
    var age: Int,
    var phoneNumber: String,
    val isLogin: Boolean
)