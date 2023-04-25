package com.kai.storyapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.LoginResult
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }
//
//    fun logout() {
//        viewModelScope.launch {
//            pref.logout()
//        }
//    }

}