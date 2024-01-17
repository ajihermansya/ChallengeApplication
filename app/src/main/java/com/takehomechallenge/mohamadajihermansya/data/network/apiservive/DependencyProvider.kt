package com.takehomechallenge.mohamadajihermansya.data.network.apiservive

import com.takehomechallenge.mohamadajihermansya.data.app.Apps
import com.takehomechallenge.mohamadajihermansya.data.Repository.ReposytoryData
import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersId
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersQuery
import com.takehomechallenge.mohamadajihermansya.data.localcharacters.CharactersEpisodes
import com.takehomechallenge.mohamadajihermansya.ui.ui_detail_characters.view_model.ViewModelFactoryCharacter
import com.takehomechallenge.mohamadajihermansya.ui.ui_list_characters.viemodel.ViewModelFactoryCharacters


object DependencyProvider {

    private val repository = ReposytoryData(Apps.instance.getApiService())

    private val charactersId = CharactersId(repository)
    private val charactersQuery = CharactersQuery(repository)
    private val charactersEpisodes = CharactersEpisodes(repository)
    private val filter = FilterData()

    private val characters = ViewModelFactoryCharacter(charactersId, charactersEpisodes)
    private val characterslist = ViewModelFactoryCharacters(charactersQuery, filter)

    fun CharactersViewModel() = characters
    fun CharactersListViewModel() = characterslist

}