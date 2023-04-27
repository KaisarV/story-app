package com.kai.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.view.home.HomeViewModel
import com.kai.storyapp.view.login.LoginViewModel
import com.kai.storyapp.view.register.RegisterViewModel
import com.kai.storyapp.view.story.CreateStoryViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
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
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}