package com.example.kinopoisk.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "films") val films: List<Film>
)

@JsonClass(generateAdapter = true)
data class Film(
    @Json(name = "filmId") val filmId: Int?,
    @Json(name = "nameRu") val nameRu: String?,
    @Json(name = "year") val id: String?,
    @Json(name = "countries") val countries: List<Country>,
    @Json(name = "genres") val genres: List<Genre>,
    @Json(name = "posterUrlPreview") val imageUrl: String?
)

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "country") val country: String
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "genre") val genre: String
)

@JsonClass(generateAdapter = true)
data class Description(
    @Json(name = "description") val description: String
)