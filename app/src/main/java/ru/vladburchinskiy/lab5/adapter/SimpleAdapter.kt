package ru.vladburchinskiy.lab5.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.vladburchinskiy.lab5.R
import ru.vladburchinskiy.lab5.model.Post

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    var posts: List<Post> = listOf()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: Post) {
            post.image?.let { itemView.findViewById<ImageView>(R.id.imageView).setImageResource(it) }
            itemView.findViewById<TextView>(R.id.nameTextView).text = post.name
            itemView.findViewById<TextView>(R.id.messageTextView).text = post.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size

    @SuppressLint("NotifyDataSetChanged")
    fun set(posts: List<Post>) {
        this.posts = listOf()
        this.posts = posts
        notifyDataSetChanged()
    }
}