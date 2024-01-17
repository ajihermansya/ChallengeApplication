package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.takehomechallenge.mohamadajihermansya.R
import com.takehomechallenge.mohamadajihermansya.data.model.ModelSimpleCharacters
import com.takehomechallenge.mohamadajihermansya.databinding.CardCharacterBinding

class ListCharactersAdapter : PagingDataAdapter<ModelSimpleCharacters, ListCharactersAdapter.ItemHolder>(DiffCallBAck()) {

    private var onItemClick: ((characterId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            CardCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class DiffCallBAck : DiffUtil.ItemCallback<ModelSimpleCharacters>(){
        override fun areItemsTheSame(oldItem: ModelSimpleCharacters, newItem: ModelSimpleCharacters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ModelSimpleCharacters, newItem: ModelSimpleCharacters): Boolean {
            return oldItem == newItem
        }

    }

    class ItemHolder(private val binding: CardCharacterBinding, private val onItemClick: ((characterId: Int) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: ModelSimpleCharacters){
                binding.apply {
                    charactersName.text = character.name
                    binding.root.context.also { context ->
                        ("${context.getString(R.string.species)} ${character.species}").also { charactersSpecies.text = it }
                        ("${context.getString(R.string.gender)} ${character.gender}").also { charactersGender.text = it }
                    }

                    Glide.with(itemView.context)
                        .load(character.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_download)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(charactersImage)

                    mainWrapper.setOnClickListener {
                        onItemClick?.invoke(character.id)
                    }
                }
        }

    }

    fun onItemClick(onItemClick: (characterId: Int) -> Unit){
        this.onItemClick = onItemClick
    }

}