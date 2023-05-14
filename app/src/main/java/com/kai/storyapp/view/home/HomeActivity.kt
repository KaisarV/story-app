package com.kai.storyapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.storyapp.R
import com.kai.storyapp.adapter.ListStoryAdapter
import com.kai.storyapp.adapter.LoadingStateAdapter
import com.kai.storyapp.databinding.ActivityHomeBinding
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.login.LoginActivity
import com.kai.storyapp.view.map.StoryMapsActivity
import com.kai.storyapp.view.story.CreateStoryActivity

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
            ViewModelFactory(this)
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                homeViewModel.getStory()
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

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        homeViewModel.story.observe(this) { story->
            if (story !== null) {
                adapter.submitData(lifecycle, story)
            }
        }
    }

    private fun setupAction() {
        binding.postingPageButton.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }
    }
}