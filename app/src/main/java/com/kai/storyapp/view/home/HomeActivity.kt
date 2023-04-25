package com.kai.storyapp.view.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.storyapp.R
import com.kai.storyapp.adapter.ListStoryAdapter
import com.kai.storyapp.databinding.ActivityHomeBinding
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        setupView()
        setupViewModel()
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
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                binding.greeting.text = getString(R.string.greeting, user.name)
                homeViewModel.getStory(user.token)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        homeViewModel.listStory.observe(this) { stories ->
            setUserData(stories.listStory)
        }
    }

    private fun setUserData(stories: List<ListStoryItem>) {

        for(story in stories){
            Log.d("NIHHHHHHHHHHHHH", story.id)
        }

        val listUserAdapter = ListStoryAdapter(stories)

        binding.rvUser.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}