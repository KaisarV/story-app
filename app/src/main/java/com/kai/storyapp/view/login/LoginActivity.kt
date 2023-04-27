package com.kai.storyapp.view.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.kai.storyapp.R
import com.kai.storyapp.databinding.ActivityLoginBinding
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.utils.Validator.isValidInputEmail
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.home.HomeActivity
import com.kai.storyapp.view.register.RegisterActivity
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {



    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
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
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupAction() {

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            when {
                email.isEmpty() -> {
                    binding.email.error = "Email cannot be empty"
                }
                password.isEmpty() -> {
                    binding.password.error = "Password cannot be empty"
                }
                !isValidInputEmail(binding.email.text.toString()) -> {
                    binding.email.error = "Email is not valid"
                }

                else -> {
                    loginViewModel.loginUser(email, password)

                    loginViewModel.loginResponse.observe(this) { loginResponse ->
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
                        Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show()
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