package com.takehomechallenge.mohamadajihermansya.data.network.apiservice

import com.takehomechallenge.mohamadajihermansya.data.model.ModelCharacters
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.data.model.ResponseServer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    suspend fun loadAllCharacters(@Query("page") page: Int? = 1,
                                  @Query("name") name: String? = null,
                                  @Query("status") status: String? = null,
                                  @Query("gender") gender: String? = null): Response<ResponseServer>

    @GET("character/{ids}")
    suspend fun loadFullCharactersByIds(@Path("ids") ids: String): Response<List<ModelCharacters>>

    @GET("episode/{ids}")
    suspend fun loadEpisodesByIds(@Path("ids") ids: String): Response<List<ModelEpisodes.Episode>>

}