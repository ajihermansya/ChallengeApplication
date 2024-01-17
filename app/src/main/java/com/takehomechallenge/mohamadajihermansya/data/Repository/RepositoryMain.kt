package com.takehomechallenge.mohamadajihermansya.data.Repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import retrofit2.Response
import com.takehomechallenge.mohamadajihermansya.data.model.ModelCharacters
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.data.model.ModelSimpleCharacters
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData

interface RepositoryMain {

    fun getFilterCharacters(string: FilterData): LiveData<PagingData<ModelSimpleCharacters>>
    fun getFullListCharacters(id: Int): LiveData<PagingData<ModelCharacters>>
    suspend fun getIdEpisodes(ids: String): Response<List<ModelEpisodes.Episode>>?

}