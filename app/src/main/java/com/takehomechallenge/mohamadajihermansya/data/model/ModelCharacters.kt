package com.takehomechallenge.mohamadajihermansya.data.model

data class ModelCharacters (
    val id : Int,
    val name : String,
    val status : String,
    val species : String,
    val type : String,
    val gender : String,
    val origin : Origin,
    val location : Location,
    val image : String,
    val episode : List<String>,
){

    var episodesChild : ModelEpisodes? = null

    private var listener: ((episodesChild: List<ModelEpisodes.Episode>?) -> Unit)? = null

    data class Origin (
        val name : String,
        val url : String
    )

    data class Location (
        val name : String,
        val url : String
    )


    fun bind(episodes: List<ModelEpisodes.Episode>?){
        episodesChild?.apply {
            this.episodes = episodes
            listener?.invoke(episodesChild?.episodes)
        }
    }
    fun bindListener(listener: ((episodesChild: List<ModelEpisodes.Episode>?) -> Unit)?){
        this.listener = listener
    }



}
