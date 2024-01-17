package com.takehomechallenge.mohamadajihermansya.data.localcharacters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.takehomechallenge.mohamadajihermansya.data.model.ModelEpisodes
import com.takehomechallenge.mohamadajihermansya.data.Repository.RepositoryMain

class CharactersEpisodes(private val repo: RepositoryMain) {

    private var flow = MutableStateFlow<ModelEpisodes?>(null)

    suspend fun execute(episodes: List<String>, modelEpisodes: ModelEpisodes): Flow<ModelEpisodes> {
        var data = ""
        episodes.map {
            data = "$data${it.substring(it.lastIndexOf("/")+1)},"
        }

        modelEpisodes.episodes = repo.getIdEpisodes(data)?.body()
        flow.value = modelEpisodes
        return flow as Flow<ModelEpisodes>
    }

}