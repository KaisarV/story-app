package com.kai.storyapp.view.register

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.request.LoginRequest
import com.kai.storyapp.model.request.RegisterRequest
import com.kai.storyapp.model.response.ErrorResponse
import com.kai.storyapp.model.response.LoginResponse
import com.kai.storyapp.model.response.RegisterResponse
import com.kai.storyapp.model.response.StoryResponse
import com.kai.storyapp.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun registerUser(registerRequest : RegisterRequest){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val register = apiService.registerUser(registerRequest)

        register.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _registerResponse.value = response.body()
                    }
                }else {
                    val errBody = response.errorBody()
                    val errJsonString = errBody?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errJsonString, ErrorResponse::class.java)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}