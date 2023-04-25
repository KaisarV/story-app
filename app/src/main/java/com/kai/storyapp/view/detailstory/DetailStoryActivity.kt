package com.kai.storyapp.view.detailstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kai.storyapp.R
import com.kai.storyapp.model.response.ListStoryItem

class DetailStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
        setupData()
    }

    private fun setupData() {
        val story = intent.getParcelableExtra<ListStoryItem>("Story") as ListStoryItem
        Glide.with(applicationContext)
            .load(story.photoUrl)
            .into(findViewById(R.id.storyImageView))


        findViewById<TextView>(R.id.nameTextView).text = story.name
        findViewById<TextView>(R.id.descTextView).text = story.description
        findViewById<TextView>(R.id.timestamp).text = story.createdAt
    }
}