package com.takehomechallenge.mohamadajihermansya.data.localcharacters

import com.takehomechallenge.mohamadajihermansya.data.model.FilterData
import com.takehomechallenge.mohamadajihermansya.data.Repository.RepositoryMain

class CharactersQuery(private val repo: RepositoryMain) {

    fun execute(query: FilterData) = repo.getFilterCharacters(query)

}