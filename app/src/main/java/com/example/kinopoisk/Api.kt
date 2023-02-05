package com.example.kinopoisk

import com.example.kinopoisk.data.ApiData
import com.example.kinopoisk.data.Description
import com.example.kinopoisk.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FilmsApi {
    @Headers("X-API-KEY:  e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1")
    suspend fun getListOfFilms(): ApiData

    @Headers("X-API-KEY:  e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/{id}")
    suspend fun getDescription(@Path("id") id: String): Description
}

object FilmsApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .build()

    private val filmsService = retrofit.create(FilmsApi::class.java)

    suspend fun getListOfFilms(): List<Movie> {
        var id: String
        return withContext(Dispatchers.IO) {
            filmsService.getListOfFilms()
                .films
                .map { result ->
                    id = result.filmId.toString()
                    Movie(
                        result.filmId,
                        result.nameRu,
                        result.id,
                        result.countries
                            .map { res ->
                                res.country
                            }.joinToString(),
                        result.genres
                            .map { r ->
                                r.genre
                         }.joinToString(),
                        result.imageUrl,
                        filmsService.getDescription(id).description
                    )
                }
        }
    }
}