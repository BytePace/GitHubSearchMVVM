package com.drus.githubsearch.search.screens.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.drus.githubsearch.search.databinding.ItemHolderRepositoryBinding
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo

class RepositoriesAdapter(
    private val onItemSelected: (SimpleRepositoryInfo) -> Unit
) : PagedListAdapter<SimpleRepositoryInfo, RepositoriesAdapter.ViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHolderRepositoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ViewHolder(private val binding: ItemHolderRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SimpleRepositoryInfo?) {
            item?.let { info ->
                binding.root.setOnClickListener { onItemSelected(info) }
                binding.textRepository.text = info.repositoryName
                binding.textOwner.text = info.repositoryOwner.userName
                binding.textDate.text = info.date
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimpleRepositoryInfo>() {
            override fun areItemsTheSame(
                oldItem: SimpleRepositoryInfo,
                newItem: SimpleRepositoryInfo
            ): Boolean {
                return oldItem.repositoryURL == newItem.repositoryURL
            }

            override fun areContentsTheSame(
                oldItem: SimpleRepositoryInfo,
                newItem: SimpleRepositoryInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}