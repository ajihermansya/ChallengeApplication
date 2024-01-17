package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.databinding.DetailEpisodesBinding

class AdapterEpisodes(private var episodes: List<ModelEpisodes.Episode>): RecyclerView.Adapter<AdapterEpisodes.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        DetailEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class ItemHolder(val binding: DetailEpisodesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(episode: ModelEpisodes.Episode){
            binding.apply {
                charactersId.text = episode.episode
                charactersName.text = episode.name
                charactersDate.text = episode.air_date
                charactersDate.text = episode.air_date
            }
        }

    }

}