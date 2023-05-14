package com.kai.storyapp.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.kai.storyapp.data.StoryPagingSource
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ErrorResponse
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LocationResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(private val apiService: ApiService, private val pref: UserPreference) {
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref.getToken())
            }
        ).liveData
    }

    fun getStoryLocation(token: String): LiveData<LocationResponse> {
        val storyMapResponseLiveData = MutableLiveData<LocationResponse>()

        val client = apiService.storyLocation("Bearer $token", "1")
        client.enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        storyMapResponseLiveData.value = response.body()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

        return storyMapResponseLiveData
    }
}
