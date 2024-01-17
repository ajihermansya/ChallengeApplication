package com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersId
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersEpisodes

class ViewModelFactoryCharacter(
    private val charactersId: CharactersId,
    private val charactersEpisodes: CharactersEpisodes
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelCharacter(charactersId, charactersEpisodes) as T

}