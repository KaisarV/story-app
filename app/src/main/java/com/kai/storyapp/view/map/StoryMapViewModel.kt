package com.kai.storyapp.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kai.storyapp.model.response.LocationResponse
import com.kai.storyapp.model.response.LoginResult
import com.kai.storyapp.repository.StoryRepository

class StoryMapViewModel(private val repo : StoryRepository) : ViewModel() {
    private val _storyMapResponse = MutableLiveData<LocationResponse>()
    val storyMapResponse: LiveData<LocationResponse> = _storyMapResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStoryLocation(token: String) {
        _isLoading.value = true

        val storyMapResponseLiveData = repo.getStoryLocation(token)

        storyMapResponseLiveData.observeForever { locationResponse ->
            _storyMapResponse.value = locationResponse
            _isLoading.value = false
        }
    }

    fun getUser(): LiveData<LoginResult> {
        return repo.getUser()
    }

}