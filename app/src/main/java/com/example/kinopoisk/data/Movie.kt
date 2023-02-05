package com.example.kinopoisk.data

import java.io.Serializable

data class Movie(
    var filmId: Int? = null,
    var nameRu: String? = null,
    var year: String? = null,
    var country: String? = null,
    var genre: String,
    var imageUrl: String? = null,
    var info: String? = null
): Serializable