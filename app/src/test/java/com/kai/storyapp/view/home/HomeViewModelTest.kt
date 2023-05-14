package com.kai.storyapp.view.home

import org.junit.Assert.*

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

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