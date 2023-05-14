package com.kai.storyapp.view.home

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kai.storyapp.di.Injection
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LoginResult

class HomeViewModel(val context : Context) : ViewModel() {

    lateinit var story: LiveData<PagingData<ListStoryItem>>

    fun getStory(){
        story = Injection.provideRepository(context).getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<LoginResult> {
        return Injection.provideAuthRepository(context).getUser()
    }

    fun logout() {
        return Injection.provideAuthRepository(context).logout()
    }
}