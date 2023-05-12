package com.kai.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.storyapp.databinding.StoryItemBinding
import com.kai.storyapp.model.response.ListStoryItem
import com.kai.storyapp.utils.DateFormatter
import com.kai.storyapp.view.detailstory.DetailStoryActivity

class ListStoryAdapter :
    PagingDataAdapter<ListStoryItem, ListStoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ListViewHolder(private val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var imgPhoto: ImageView = binding.image
        private var tvName: TextView = binding.name
        private var tvDescription: TextView = binding.description
        private var tvTimeStamp: TextView = binding.timestamp

        fun bind(storyItem: ListStoryItem) {

            Glide.with(itemView.context)
                .load(storyItem.photoUrl)
                .into(imgPhoto)
            tvName.text = storyItem.name
            tvDescription.text = storyItem.description
            tvTimeStamp.text = DateFormatter.formatDate(storyItem.createdAt, "Asia/Jakarta")

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

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}