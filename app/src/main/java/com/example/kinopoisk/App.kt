package com.example.kinopoisk

import android.app.Application
import com.example.kinopoisk.sqlite.DBHelper

class App: Application() {
    val  dbHelper: DBHelper = DBHelper(this)
}