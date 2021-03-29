package com.dj.intentparsedatamvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dj.intentparsedatamvvm.data.model.Issue
import com.dj.intentparsedatamvvm.databinding.ItemCardLayoutBinding

class MainAdapter : ListAdapter<Issue, IssueViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            ItemCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Issue>() {
            override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class IssueViewHolder(private val binding: ItemCardLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(issue: Issue) {
        binding.tvDesc.text = "#${issue.number} ${issue.title}"
    }
}