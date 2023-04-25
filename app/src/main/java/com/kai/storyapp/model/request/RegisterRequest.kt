package com.kai.storyapp.model.request

data class RegisterRequest (
    var name: String,
    var email: String,
    var password: String
)