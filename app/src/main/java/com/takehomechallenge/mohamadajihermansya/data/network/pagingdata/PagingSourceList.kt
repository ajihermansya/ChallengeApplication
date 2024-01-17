package com.takehomechallenge.mohamadajihermansya.data.network.pagingdata

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.ApiService
import com.takehomechallenge.mohamadajihermansya.data.model.ResponseServer
import com.takehomechallenge.mohamadajihermansya.data.model.ModelSimpleCharacters
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData

class PagingSourceList(private val apiService: ApiService, private val filters: FilterData): PagingSource<Int, ModelSimpleCharacters>() {

    override fun getRefreshKey(state: PagingState<Int, ModelSimpleCharacters>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelSimpleCharacters> {
        val pageIndex = params.key ?: 1

        return try {
            val response: Response<ResponseServer> = apiService.loadAllCharacters(pageIndex, filters.name, filters.status, filters.gender)

            if(response.code() == 404){
                throw Exception(response.code().toString())
            }

            LoadResult.Page(
                response.body()!!.results,
                if(pageIndex == 1) null else pageIndex - 1,
                if(pageIndex == response.body()!!.info.pages) null else pageIndex + 1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

}