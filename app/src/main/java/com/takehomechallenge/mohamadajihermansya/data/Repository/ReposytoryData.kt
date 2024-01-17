package com.takehomechallenge.mohamadajihermansya.data.Repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import retrofit2.Response
import com.takehomechallenge.mohamadajihermansya.data.network.pagingdata.PagingSourceDetail
import com.takehomechallenge.mohamadajihermansya.data.network.pagingdata.PagingSourceList
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.ApiService
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData

class ReposytoryData(private val api: ApiService): RepositoryMain {

    override fun getFilterCharacters(filter: FilterData) = Pager(
           PagingConfig(
               pageSize = 20,
               prefetchDistance = 2,
           ),
           pagingSourceFactory = { PagingSourceList(api, filter) }
       ).liveData

    override fun getFullListCharacters(id: Int) = Pager(
        PagingConfig(
            pageSize = 2,
            initialLoadSize = 2
        ),
        pagingSourceFactory = { PagingSourceDetail(api, id) }
        ).liveData

    override suspend fun getIdEpisodes(ids: String): Response<List<ModelEpisodes.Episode>>? {
        return try {
            api.loadEpisodesByIds(ids)
        }   catch (e: Exception){
            null
        }
    }


}