package com.kai.storyapp.view.register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
import com.kai.storyapp.model.UserModel
import com.kai.storyapp.utils.Validator
import com.kai.storyapp.view.ViewModelFactory


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
    }

    override fun onClick(view: View) {
        if (view.id == R.id.register) {
            registerButton = binding.register
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val age = binding.age.text.toString().trim()
            val phone = binding.phone.text.toString().trim()

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

            if (age.isEmpty()) {
                binding.age.error = "Age cannot be empty"
                return
            }

            if (phone.isEmpty()) {
                binding.phone.error = "Phone cannot be empty"
                return
            }

            else {
                registerViewModel.saveUser(UserModel(name, email, password, age.toInt(), phone, false))
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akunnya sudah jadi nih. Yuk, login dan belajar coding.")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }
}