package com.mati.mimovies.data.model

data class Movies(

    val page: Int?,
    val results: List<Results>,
) {

    data class Results(

        val id: Long?,
        val orginal_title: String?,
        val overview: String?,
        val poster_path: String?,
        val vote_average: Float?,


        )


}
