package com.example.kinopoisk.sqlite

import android.content.ContentValues
import android.content.Context
import com.example.kinopoisk.data.Movie
import com.example.kinopoisk.App


class DBOperation {

    fun getFilmsFromBD(applicationContext: Context, sort: String) : List<Movie> {
        val list = mutableListOf<Movie>()
        val coursore = (applicationContext as App)
            .dbHelper
            .readableDatabase
            .query("films", null, null, null, null, null, sort)
        if(coursore != null) {
            val idIndex = coursore.getColumnIndex("id")
            val nameIndex = coursore.getColumnIndex("name")
            val yearIndex = coursore.getColumnIndex("year")
            val countryIndex = coursore.getColumnIndex("country")
            val genreIndex = coursore.getColumnIndex("genre")
            val imageUrlIndex = coursore.getColumnIndex("imageUrl")
            val infoIndex = coursore.getColumnIndex("info")
            while (coursore.moveToNext()) {
                list.add(Movie(coursore.getInt(idIndex),
                    coursore.getString(nameIndex),
                    coursore.getString(yearIndex),
                    coursore.getString(countryIndex),
                    coursore.getString(genreIndex),
                    coursore.getString(imageUrlIndex),
                    coursore.getString(infoIndex),))
            }
            coursore.close()
            return list
        }
        return emptyList()
    }

    fun saveFilm(applicationContext: Context, id:Int, name:String, year:Int, country:String,
                 genre:String, imageUrl: String, info: String){
        val contentValues = ContentValues().apply {
            put("id", id)
            put("name", name)
            put("year", year)
            put("country", country)
            put("genre", genre)
            put("imageUrl", imageUrl)
            put("info", info)
        }
        (applicationContext as App)
            .dbHelper
            .readableDatabase
            .insert("films", null, contentValues)
    }

    fun deleteFilm(applicationContext: Context, position:Int, sort: String){
        (applicationContext as App)
            .dbHelper
            .readableDatabase
            .delete("films", "" + getFilmsFromBD(applicationContext, sort)[position].filmId + " =id", null)
    }
}