package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehomechallenge.mohamadajihermansya.databinding.LoadingStateBinding


class StateCharactersAdapter(private val retry: (() -> Unit)? = null) : LoadStateAdapter<StateCharactersAdapter.ItemHolder>() {

    override fun onBindViewHolder(holder: ItemHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ItemHolder(
        LoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class ItemHolder(private val binding: LoadingStateBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.buttonRetry.setOnClickListener {
                retry?.invoke()
            }
        }

        fun bind(loadState: LoadState){

            binding.apply {
                mainProgress.isVisible = loadState is LoadState.Loading
                errorWrapper.isVisible = loadState !is LoadState.Loading
            }
        }

    }

}