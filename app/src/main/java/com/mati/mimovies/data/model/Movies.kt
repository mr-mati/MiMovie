package com.mati.mimovies.data.model

data class Movies(

    val page: Int?,
    val results: List<Results>,
) {

    data class Results(

        val id: Long? = 0,
        val orginal_title: String? = "",
        val title: String? = "Null Name",
        val genre_ids: List<Int>? = listOf(0, 0),
        val overview: String? = "",
        val poster_path: String? = "",
        val release_date: String? = "",
        val vote_average: String? = "",
    )


}
