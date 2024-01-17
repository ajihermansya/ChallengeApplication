package com.takehomechallenge.mohamadajihermansya.data.model

data class ModelEpisodes(
    val id: Int?,
    var episodes: List<Episode>? = null
){
    data class Episode(
        val name: String,
        val air_date: String,
        val episode: String
    )
}