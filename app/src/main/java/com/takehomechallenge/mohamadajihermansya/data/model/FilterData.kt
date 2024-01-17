package com.takehomechallenge.mohamadajihermansya.data.model

data class FilterData(
    var name: String? = null,
    var status: String? = null,
    var gender: String? = null
    ){


    var statusId = 0
    var genderId = 0


    enum class Status(val value: String){
        ALIVE(FilterData.ALIVE),
        DEAD(FilterData.DEAD),
        UNKNOWN(FilterData.UNKNOWN)
    }

    enum class Gender(val value: String){
        FEMALE(FilterData.FEMALE),
        MALE(FilterData.MALE),
        GENDERLESS(FilterData.GENDERLESS),
        UNKNOWN(FilterData.UNKNOWN)
    }


    private companion object {
        private const val ALIVE = "Alive"
        private const val DEAD = "Dead"
        private const val MALE = "Male"
        private const val FEMALE = "Female"
        private const val GENDERLESS = "Genderless"
        private const val UNKNOWN = "unknown"
    }

}