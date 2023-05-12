package com.kai.storyapp.view.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.storyapp.R
import com.kai.storyapp.adapter.ListStoryAdapter
import com.kai.storyapp.databinding.ActivityHomeBinding
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.login.LoginActivity
import com.kai.storyapp.view.map.StoryMapsActivity
import com.kai.storyapp.view.register.RegisterActivity
import com.kai.storyapp.view.story.CreateStoryActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        setupViewModel()
        setupAction()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                homeViewModel.logout()
            }
            R.id.menu2 -> {
                finishAffinity()
            }
            R.id.story_map ->{
                startActivity(Intent(this, StoryMapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                homeViewModel.fillRepo(user.token)
                binding.greeting.text = getString(R.string.greeting, user.name)
                getData()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun getData() {
        val adapter = ListStoryAdapter()
        binding.rvStory.adapter = adapter
            homeViewModel.story.observe(this) { story->
                if (story !== null) {
                    adapter.submitData(lifecycle, story)
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

    private fun setupAction() {
        binding.postingPageButton.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }
    }
}