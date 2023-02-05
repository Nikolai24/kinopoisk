package com.example.kinopoisk.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "films", null, 1) {
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL("CREATE TABLE films (id INTEGER Primary key autoincrement NOT NULL, " +
                "name TEXT NOT NULL, year Text NOT NULL, country TEXT NOT NULL, " +
                "genre TEXT NOT NULL, imageUrl TEXT NOT NULL, info TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
}
