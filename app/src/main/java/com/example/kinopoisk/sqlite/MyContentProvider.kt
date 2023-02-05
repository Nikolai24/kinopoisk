package com.example.kinopoisk.sqlite

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.example.kinopoisk.App

public class MyContentProvider : ContentProvider() {
    private lateinit var db: SQLiteDatabase
    override fun onCreate(): Boolean {
        db = (context as App)
            .dbHelper
            .writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?): Cursor? {
        return when (uriMatcher.match(uri)) {
            DATA -> {
                val cursor = db.query("films", null, selection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            DATA -> {
                val id = db.insert("films", null, values)
                context?.contentResolver?.notifyChange(uri, null)
                Uri.parse("\"films\"/$id")
            }
            else -> null
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            DATA -> {
                val rowUpdated = db.update("films", values, selection, selectionArgs)
                context?.contentResolver?.notifyChange(uri, null)
                rowUpdated
            }
            else -> -1
        }
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            DATA -> {
                val rowDeleted = db.delete("films", selection, selectionArgs)
                context?.contentResolver?.notifyChange(uri, null)
                rowDeleted
            }
            else -> -1
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            DATA -> "object/uri"
            else -> null
        }
    }

    companion object {
        private const val AUTHORITY = "com.example.kinopoisk"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/films")
        private const val DATA = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "films", DATA)
        }
    }
}