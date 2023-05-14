package com.kai.storyapp

import com.kai.storyapp.model.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story= ListStoryItem(
                photoUrl = "https://example.com/photo.jpg",
                createdAt = "2023-05-14T12:34:56Z",
                name = "Dummy Story",
                description = "This is a dummy story item",
                lon = "123.456",
                id = "1234567890",
                lat = "78.90"
            )
            items.add(story)
        }
        return items
    }
}
