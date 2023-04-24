package com.kai.storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kai.storyapp.customview.login.LoginButton
import com.kai.storyapp.customview.login.LoginInputEditText
import com.kai.storyapp.databinding.ActivityMainBinding
import java.util.regex.Pattern

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
                email.error = "Password cannot be empty"
            }
        }

    }

    fun isValidInputEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


}