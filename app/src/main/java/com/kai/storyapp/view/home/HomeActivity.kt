package com.kai.storyapp.view.home
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.storyapp.R
import com.kai.storyapp.adapter.ListStoryAdapter
import com.kai.storyapp.adapter.LoadingStateAdapter
import com.kai.storyapp.databinding.ActivityHomeBinding
import com.kai.storyapp.view.ViewModelFactory
import com.kai.storyapp.view.login.LoginActivity
import com.kai.storyapp.view.map.StoryMapsActivity
import com.kai.storyapp.view.story.CreateStoryActivity
import androidx.activity.viewModels

class HomeActivity : AppCompatActivity() {

    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: ListStoryAdapter
    private val homeViewModel: HomeViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        setupView(this)
        setupViewModel()
        getStories()
        setupAction()
    }


    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)

        homeViewModel.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                binding.greeting.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun setupView(context: Context) {
        val storiesRv = binding.rvStory

        storiesRv.layoutManager = LinearLayoutManager(context)

        adapter = ListStoryAdapter()
        storiesRv.adapter = adapter
    }

    private fun getStories() {

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        homeViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rvStory.scrollToPosition(0)
            }, 300)
        }
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

    private fun setupAction() {
        binding.postingPageButton.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }
    }
}