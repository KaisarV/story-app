package com.kai.storyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kai.storyapp.data.StoryPagingSource
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
}
