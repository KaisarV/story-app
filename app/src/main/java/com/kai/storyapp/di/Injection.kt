package com.kai.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kai.storyapp.data.StoryRepository
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.retrofit.ApiConfig
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val dataStore = context.dataStore
        val pref = UserPreference.getInstance(dataStore)
        val apiService = ApiConfig().getApiService()
        return StoryRepository(apiService, pref)
    }
}