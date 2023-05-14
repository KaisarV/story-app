package com.kai.storyapp.view.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kai.storyapp.R
import com.kai.storyapp.customview.login.LoginButton
import com.kai.storyapp.customview.login.LoginInputEditText
import com.kai.storyapp.customview.login.PasswordInputEditText
import com.kai.storyapp.databinding.ActivityLoginBinding
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.utils.Validator.isValidInputEmail
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.home.HomeActivity
import com.kai.storyapp.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var passwordEditText: PasswordInputEditText
    private lateinit var emailEditText: LoginInputEditText
    private lateinit var loginButton: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordEditText = binding.password
        emailEditText = binding.email
        loginButton = binding.login

        setupView()
        setupViewModel()
        setupAction()
        setButtonEnable()

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setButtonEnable() {
        val result = passwordEditText.text
        loginButton.isEnabled = result != null && isMoreEqualThanEight(result.toString())
    }

    private fun isMoreEqualThanEight(text : String): Boolean{
        if(text.length < 8){
            return false
        }
        return true
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupAction() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password =  passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    emailEditText.error = getString(R.string.email_not_empty)
                }
                password.isEmpty() -> {
                    passwordEditText.error = getString(R.string.password_not_empty)
                }
                !isValidInputEmail(binding.email.text.toString()) -> {
                    emailEditText.error = getString(R.string.email_not_valid)
                }

                else -> {
                    loginViewModel.loginUser(email, password)

                    loginViewModel.loginResponse.observe(this) { loginResponse ->
                        Log.d(TAG, "success response")
                        Toast.makeText(this, loginResponse.message, Toast.LENGTH_SHORT).show()
                        val login = LoginResult(loginResponse.loginResult.name,
                            loginResponse.loginResult.userId, loginResponse.loginResult.token)
                        loginViewModel.login(login)

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }

                    loginViewModel.errorResponse.observe(this) { errorResponse ->
                        if(errorResponse.message != null){
                            Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }

        binding.register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}