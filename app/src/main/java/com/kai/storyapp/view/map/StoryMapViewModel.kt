package com.kai.storyapp.view.map

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kai.storyapp.di.Injection
import com.kai.storyapp.model.UserPreference
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

    fun getStoryLocation(token : String) {
        _isLoading.value = true
        val client = ApiConfig().getApiService().storyLocation("Bearer $token", "1")
        client.enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _storyMapResponse.value = response.body()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return Injection.provideAuthRepository(context).getUser()
    }

}