package com.kai.storyapp.di

import android.content.Context
import android.util.Log
import com.kai.storyapp.data.StoryRepository
import com.kai.storyapp.retrofit.ApiConfig

object Injection {
    fun provideRepository(token: String): StoryRepository {
        val apiService = ApiConfig().getApiService()
        Log.d("TOOOKEN", token)
        return StoryRepository(apiService, token)
    }
}