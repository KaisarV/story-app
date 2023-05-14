package com.kai.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import com.kai.storyapp.di.Injection
import com.kai.storyapp.repository.StoryRepository
import com.kai.storyapp.view.home.HomeViewModel
import com.kai.storyapp.view.login.LoginViewModel
import com.kai.storyapp.view.map.StoryMapViewModel
import com.kai.storyapp.view.register.RegisterViewModel
import com.kai.storyapp.view.story.CreateStoryViewModel

class ViewModelFactory private constructor(private val repo: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repo) as T
            }
            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(repo) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }
            modelClass.isAssignableFrom(StoryMapViewModel::class.java) -> {
                StoryMapViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}
