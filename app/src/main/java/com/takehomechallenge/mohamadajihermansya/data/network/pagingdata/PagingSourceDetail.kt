package com.takehomechallenge.mohamadajihermansya.data.network.pagingdata

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import com.takehomechallenge.mohamadajihermansya.data.network.apiservice.ApiService
import com.takehomechallenge.mohamadajihermansya.data.model.ModelCharacters

class PagingSourceDetail(private val apiService: ApiService, private val characterId: Int): PagingSource<Int, ModelCharacters>() {

    override fun getRefreshKey(state: PagingState<Int, ModelCharacters>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelCharacters> {

        val pageSize = params.loadSize
        val pageIndex = params.key ?: (characterId / params.loadSize)
        val characterOffset = characterId % params.loadSize
        val characterIndex = (pageIndex * pageSize) + characterOffset

        return try {
            val response: Response<List<ModelCharacters>> = apiService.loadFullCharactersByIds(stringComma(characterIndex, characterIndex + 1))
            val charactersCount = apiService.loadAllCharacters().body()!!.info.count

            LoadResult.Page(
                response.body()!!,
                if(pageIndex == 0) null else pageIndex - 1,
                if(pageIndex == charactersCount) null else pageIndex + 1
            )

        }catch (e: Exception){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

    private fun stringComma(vararg numbers: Int): String {
        var result = ""
        if(numbers.size == 1) return numbers.toString()
        numbers.map { result = "$result$it," }
        return result
    }

}
