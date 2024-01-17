package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersQuery

class ViewModelFactoryCharacters(
    private val charactersQuery: CharactersQuery,
    private val filter: FilterData
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelListCharacters(charactersQuery, filter) as T

}