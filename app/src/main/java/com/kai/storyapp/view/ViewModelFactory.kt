package com.kai.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kai.storyapp.di.Injection
import android.content.Context
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.view.home.HomeActivity
import com.kai.storyapp.view.home.HomeViewModel
import com.kai.storyapp.view.login.LoginViewModel
import com.kai.storyapp.view.map.StoryMapViewModel
import com.kai.storyapp.view.register.RegisterViewModel
import com.kai.storyapp.view.story.CreateStoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(context) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(StoryMapViewModel::class.java) -> {
                StoryMapViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}