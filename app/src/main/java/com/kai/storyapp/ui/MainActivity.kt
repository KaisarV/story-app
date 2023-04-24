package com.kai.storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kai.storyapp.customview.login.LoginButton
import com.kai.storyapp.customview.login.LoginInputEditText
import com.kai.storyapp.databinding.ActivityMainBinding
import com.kai.storyapp.utils.Validator.isValidInputEmail

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: LoginButton
    private lateinit var email: LoginInputEditText
    private lateinit var password: LoginInputEditText
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginButton = binding.login
        email = binding.email
        password = binding.password

        loginButton.setOnClickListener{
            if (!isValidInputEmail(email.text.toString())) {
                email.error = "Email is not valid"
            }

            if (password.text.toString() == "") {
                password.error = "Password cannot be empty"
            }
        }
    }
}