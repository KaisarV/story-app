package com.kai.storyapp.view.map

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kai.storyapp.di.Injection
import com.kai.storyapp.model.response.LocationResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryMapViewModel(private val context : Context) : ViewModel() {
    private val _storyMapResponse = MutableLiveData<LocationResponse>()
    val storyMapResponse: LiveData<LocationResponse> = _storyMapResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStoryLocation(token: String) {
        _isLoading.value = true

        val storyMapResponseLiveData = Injection.provideRepository(context).getStoryLocation(token)

        storyMapResponseLiveData.observeForever { locationResponse ->
            _storyMapResponse.value = locationResponse
            _isLoading.value = false
        }
    }

    fun getUser(): LiveData<LoginResult> {
        return Injection.provideAuthRepository(context).getUser()
    }

}