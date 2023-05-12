package com.kai.storyapp.view.home


import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kai.storyapp.data.StoryRepository
import com.kai.storyapp.di.Injection
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.model.response.LoginResult
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference) : ViewModel() {

    lateinit var story: LiveData<PagingData<ListStoryItem>>

    fun fillRepo(token : String){
        story = Injection.provideRepository(token).getQuote().cachedIn(viewModelScope)
        Log.d("aaaaaaa", story.value.toString())
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}