package com.kai.storyapp.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.LoginResult

class CreateStoryViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }
}