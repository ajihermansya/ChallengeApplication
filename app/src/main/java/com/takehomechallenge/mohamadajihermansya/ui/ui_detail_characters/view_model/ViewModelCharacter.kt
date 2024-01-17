package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.view_model

import androidx.lifecycle.*
import com.takehomechallenge.mohamadajihermansya.data.model.ModelCharacters
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersId
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersEpisodes
import kotlinx.coroutines.flow.*

class ViewModelCharacter(
    private val charactersId: CharactersId,
    private val charactersEpisodes: CharactersEpisodes
) : ViewModel() {

    private val livedata = MutableLiveData<ModelCharacters>(null)
    val episodesFlow = livedata.asFlow().flatMapConcat { charactersEpisodes.execute(it.episode, it.episodesChild!!) }

    fun fetchResults(id: Int) = charactersId.execute(id)

    fun fetchEpisodes(character: ModelCharacters){
        livedata.value = character
    }


}