package com.kai.storyapp.view.register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.kai.storyapp.R
import com.kai.storyapp.customview.login.LoginInputEditText
import com.kai.storyapp.customview.login.PasswordInputEditText
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.customview.login.RegisterButton
import com.kai.storyapp.databinding.ActivityRegisterBinding
import com.kai.storyapp.model.request.RegisterRequest
import com.kai.storyapp.utils.Validator
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerButton: RegisterButton
    private lateinit var passwordEditText: PasswordInputEditText
    private lateinit var emailEditText: LoginInputEditText
    private lateinit var nameEditText: LoginInputEditText
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordEditText = binding.password
        emailEditText = binding.email
        nameEditText = binding.name
        registerButton = binding.register

        setupView()
        setupViewModel()
        setButtonEnable()

        registerButton.setOnClickListener(this)

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
        registerButton.isEnabled = result != null && isMoreEqualThanEight(result.toString())
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
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty()) {
                nameEditText.error = getString(R.string.name_not_empty)
                return
            }

            if (!Validator.isValidInputEmail(email)) {
                emailEditText.error = getString(R.string.email_not_valid)
                return
            }

            if (password.isEmpty()) {
                passwordEditText.error = getString(R.string.password_not_empty)
                return
            }
            else {
                val registerRequest = RegisterRequest(name, email, password)
                registerViewModel.registerUser(registerRequest)

                registerViewModel.registerResponse.observe(this) { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }

                registerViewModel.errorResponse.observe(this) { response ->
                    if(response.message != null){
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }

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