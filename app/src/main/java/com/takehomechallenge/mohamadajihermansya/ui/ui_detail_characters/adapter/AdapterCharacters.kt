package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.takehomechallenge.mohamadajihermansya.R
import com.takehomechallenge.mohamadajihermansya.databinding.CharacterCardBinding
import com.takehomechallenge.mohamadajihermansya.data.model.ModelCharacters
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


class AdapterCharacters : PagingDataAdapter<ModelCharacters, AdapterCharacters.ItemHolder>(
    DiffCallBAck()
) {

    private var itemBindCallBack: ((character: ModelCharacters)-> Unit)? = null
    private var shareButtonCallback: ((characterId: Int) -> Unit)? = null
    private var episodeFlow: Flow<ModelEpisodes?>? = null
    private var lifecycleOwner: LifecycleOwner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemHolder(
            CharacterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        episodeFlow,
        lifecycleOwner,
        itemBindCallBack,
        shareButtonCallback
        )

    var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class DiffCallBAck : DiffUtil.ItemCallback<ModelCharacters>() {
        override fun areItemsTheSame(oldItem: ModelCharacters, newItem: ModelCharacters) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ModelCharacters, newItem: ModelCharacters) = oldItem == newItem
    }

    fun onItemBind(itemBindCallBack: ((character: ModelCharacters) -> Unit)?){
        this.itemBindCallBack = itemBindCallBack
    }

    fun onShareButton(shareButtonCallback: (characterId: Int)-> Unit){
        this.shareButtonCallback = shareButtonCallback
    }

    fun setFlow(episodeFlow: Flow<ModelEpisodes?>, lifecycleOwner: LifecycleOwner?){
        this.lifecycleOwner = lifecycleOwner
        this.episodeFlow = episodeFlow
    }

    inner class ItemHolder(private val binding: CharacterCardBinding, private val episodeFlow: Flow<ModelEpisodes?>?,
                           private val lifecycleOwner: LifecycleOwner?, private var itemBindCallBack: ((character: ModelCharacters)-> Unit)?,
                           private val shareButtonCallBack: ((characterId: Int)->Unit)?): RecyclerView.ViewHolder(binding.root) {

        private val linearSnapHelper = LinearSnapHelper()

        fun bind(character: ModelCharacters){

            character.episodesChild = ModelEpisodes(id = character.id)
            itemBindCallBack?.invoke(character)

            lifecycleOwner?.lifecycleScope?.launch {
                episodeFlow?.filter { it?.id == character.id }?.collect {
                    character.bind(it?.episodes) }
            }

            binding.apply {

                shareButton.setOnClickListener {
                    shareButtonCallBack?.invoke(character.id)
                }

                characterNames.text = character.name

                val originText = character.origin.name

                userName.text = character.name
                userSpecies.text = character.species
                userGender.text = character.gender
                userOrigin.text = originText


                val locationText = "Location : ${character.location.name}"
                characterLocation.text = locationText
                userLocation.text = locationText

                val stat = character.status
                val gen = character.gender
                val value = "$stat - $gen"
                characterStatus.text = value

                val circle = indicator.background.current as GradientDrawable

                val resources = root.context.resources

                when (stat) {
                    FilterData.Status.ALIVE.value -> circle.setColor(resources.getColor(R.color.purple_light))
                    FilterData.Status.DEAD.value -> circle.setColor(Color.RED)
                    FilterData.Status.UNKNOWN.value -> circle.setColor(Color.WHITE)
                }

                linearSnapHelper.attachToRecyclerView(episodes)

                character.bindListener {
                    character.episodesChild?.episodes?.apply {
                        progressBar.visibility = View.GONE
                        episodes.adapter = AdapterEpisodes(this)
                        episodes.visibility = View.VISIBLE
                    }

                }

                Glide.with(itemView.context)
                    .load(character.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_download)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mainImages)

            }
        }

    }

}