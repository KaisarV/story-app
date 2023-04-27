package com.kai.storyapp.view.register

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
import androidx.lifecycle.ViewModelProvider
import com.kai.storyapp.R
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.customview.login.RegisterButton
import com.kai.storyapp.databinding.ActivityRegisterBinding
import com.kai.storyapp.model.request.RegisterRequest
import com.kai.storyapp.model.response.RegisterResponse
import com.kai.storyapp.retrofit.ApiConfig
import com.kai.storyapp.utils.Validator
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.home.HomeActivity
import com.kai.storyapp.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerButton: RegisterButton
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

        binding.register.setOnClickListener(this)
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
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.register) {
            registerButton = binding.register
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (name.isEmpty()) {
                binding.name.error = "Name cannot be empty"
                return
            }

            if (!Validator.isValidInputEmail(email)) {
                binding.email.error = "Email is not valid"
                return
            }

            if (password.isEmpty()) {
                binding.password.error = "Password cannot be empty"
                return
            }
            else {
                val registerRequest = RegisterRequest(name, email, password)
                registerViewModel.registerUser(registerRequest)

                registerViewModel.registerResponse.observe(this) { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }

                registerViewModel.errorResponse.observe(this) { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
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