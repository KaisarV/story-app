package com.kai.storyapp.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.kai.storyapp.data.StoryRepository
import com.kai.storyapp.data.Result
import com.kai.storyapp.model.UserPreference
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.view.home.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.junit.Assert
import androidx.lifecycle.Observer
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

//    @Mock
//    private lateinit var storyRepository : StoryRepository
//    private lateinit var homeViewModel: HomeViewModel
//    private val dummyNews = DataDummy.generateDummyStoryEntity()
//    @Mock
//    private lateinit var pref : UserPreference
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        homeViewModel = HomeViewModel(pref)
//    }
//
//    val dummyToken = "token123"
//
//    @Test
//    fun `when Get Story Should Not Null and Return Success`() {
//        val observer = Observer<PagingData<ListStoryItem>> { pagingData ->
//            // Menangani perubahan PagingData di sini
//            // Anda masih dapat menggunakan Result<List<ListStoryItem>> untuk pengujian di sini
//            assertNotNull(pagingData)
//            // ...
//        }
//
//        try {
//            val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
//            expectedStory.value = PagingData.from(dummyNews)
//            `when`(storyRepository.getStory()).thenReturn(expectedStory)
//            val actualNews = homeViewModel.getStory(dummyToken).observeForever(observer)
//            Mockito.verify(storyRepository).getStory()
//            assertNotNull(actualNews)
//        } finally {
//            homeViewModel.getStory(dummyToken).removeObserver(observer)
//        }
//    }
}