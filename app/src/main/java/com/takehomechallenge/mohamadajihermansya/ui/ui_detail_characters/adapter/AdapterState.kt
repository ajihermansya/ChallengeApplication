package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehomechallenge.mohamadajihermansya.databinding.LoadingCharactersBinding

class AdapterState(private val retry: () -> Unit) : LoadStateAdapter<AdapterState.ItemHolder>() {

    override fun onBindViewHolder(holder: ItemHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ItemHolder(
        LoadingCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ItemHolder(private val binding: LoadingCharactersBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                mainProgress.isVisible = loadState is LoadState.Loading
                errorWrapper.isVisible = loadState !is LoadState.Loading
                retryButton.setOnClickListener { retry.invoke() }
            }
        }
    }
}
