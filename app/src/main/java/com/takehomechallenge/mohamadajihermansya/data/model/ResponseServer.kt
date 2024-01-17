package com.takehomechallenge.mohamadajihermansya.data.model

data class ResponseServer(
    val info: Info,
    val results: List<ModelSimpleCharacters>
){
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String = "",
        val prev: String = ""
    )
}