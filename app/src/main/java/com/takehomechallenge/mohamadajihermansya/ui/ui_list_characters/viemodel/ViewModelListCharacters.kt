package com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.viemodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersQuery

class ViewModelListCharacters(
    private val charactersQuery: CharactersQuery,
    filter: FilterData
) : ViewModel() {

    private val stateFIlter = MutableLiveData(filter)
    val simpleFilter = stateFIlter
    var results = simpleFilter.switchMap { charactersQuery.execute(it) }.cachedIn(viewModelScope)
    fun fetchResultsByFilter(filter: FilterData){
            stateFIlter.value = filter
    }

}