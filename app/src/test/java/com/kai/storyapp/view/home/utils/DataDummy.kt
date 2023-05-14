package com.kai.storyapp.view.home.utils

import com.kai.storyapp.model.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val news = ListStoryItem(
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                "name $i",
                "description $i",
                "-222",
                "$i",
                "-333"
            )
            storyList.add(news)
        }
        return storyList
    }
}