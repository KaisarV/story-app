package com.kai.storyapp.view.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.repository.StoryRepository

class HomeViewModel(private val repo : StoryRepository) : ViewModel() {


    val story: LiveData<PagingData<ListStoryItem>> =
        repo.getStoryData()

    fun getUser(): LiveData<LoginResult> {
        return repo.getUser()
    }

    fun logout() {
        return repo.logout()
    }
}