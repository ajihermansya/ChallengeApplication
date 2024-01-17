package com.takehomechallenge.mohamadajihermansya.data.localcharacters

import com.takehomechallenge.mohamadajihermansya.data.Repository.RepositoryMain

class CharactersId(private val repo: RepositoryMain) {

    fun execute(id: Int) = repo.getFullListCharacters(id)

}