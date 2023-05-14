package com.kai.storyapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.kai.storyapp.data.StoryPagingSource
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.request.LoginRequest
import com.kai.storyapp.model.response.ErrorResponse
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LoginResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository (private val apiService: ApiService, private val pref: UserPreference) {



    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        GlobalScope.launch {
            pref.logout()
        }
    }

    fun login(user: LoginResult) {
        GlobalScope.launch {
            pref.login(user)
        }
    }
}