package com.kai.storyapp.view.story

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.gson.Gson
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ErrorResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.model.response.RegisterResponse
import com.kai.storyapp.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateStoryViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _createStoryResponse = MutableLiveData<ErrorResponse>()
    val createStoryResponse: LiveData<ErrorResponse> = _createStoryResponse

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    fun createStory(token:String, imageMultipart: MultipartBody.Part, description : RequestBody){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val uploadImageRequest = apiService.uploadImage("Bearer $token", imageMultipart, description)

        uploadImageRequest.enqueue(object : Callback<ErrorResponse> {
            override fun onResponse(
                call: Call<ErrorResponse>,
                response: Response<ErrorResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        _createStoryResponse.value = response.body()
                    }
                } else {
                    val errBody = response.errorBody()
                    val errJsonString = errBody?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errJsonString, ErrorResponse::class.java)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _errorResponse.value = ErrorResponse(null, null)
            }
            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }
}