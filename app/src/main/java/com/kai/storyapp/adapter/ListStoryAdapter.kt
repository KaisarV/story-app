package com.kai.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.storyapp.R

import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.view.detailstory.DetailStoryActivity

class ListStoryAdapter(private val listStory: List<ListStoryItem>) : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imgPhoto: ImageView = itemView.findViewById(R.id.image)
        private var tvName: TextView = itemView.findViewById(R.id.name)
        private var tvDescription: TextView = itemView.findViewById(R.id.description)
        private var tvTimeStamp: TextView = itemView.findViewById(R.id.timestamp)

        fun bind(storyItem: ListStoryItem) {

            Glide.with(itemView.context)
                .load(storyItem.photoUrl)
                .into(imgPhoto)
            tvName.text = storyItem.name
            tvDescription.text = storyItem.description
            tvTimeStamp.text = storyItem.createdAt

            itemView.setOnClickListener {

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgPhoto, "profile"),
                        Pair(tvName, "name"),
                        Pair(tvDescription, "description"),
                        Pair(tvTimeStamp, "timestamp")
                    )

                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra("Story", storyItem)
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

}