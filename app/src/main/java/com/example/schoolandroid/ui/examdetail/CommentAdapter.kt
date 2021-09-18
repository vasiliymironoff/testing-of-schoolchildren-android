package com.example.schoolandroid.ui.examdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.model.Comment
import com.example.schoolandroid.databinding.LayoutCommentBinding
import com.example.schoolandroid.util.Util
import com.squareup.picasso.Picasso

class CommentAdapter(var comments: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = LayoutCommentBinding.bind(view)

        fun bind(comment: Comment) {
            Picasso.get()
                .load(comment.author.avatar)
                .into(binding.avatar)
            binding.name.text = "${comment.author.firstName} ${comment.author.lastName}"
            binding.text.text = comment.text
            binding.publishTime.text = Util.utilTimeToFormatForUI(comment.publishTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments.get(position))
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}