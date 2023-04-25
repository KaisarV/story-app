package com.kai.storyapp.view.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*

import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.request.LoginRequest
import com.kai.storyapp.model.response.LoginResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun loginUser(email: String, password: String){
        val loginRequest = LoginRequest(email, password)
        val client = ApiConfig().getApiService().loginUser(loginRequest)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loginResponse.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    fun login(user: LoginResult) {
        viewModelScope.launch {
            pref.login(user)
        }
    }
}