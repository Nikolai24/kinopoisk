package com.example.kinopoisk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.Movie
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Movie>>()
    val items: LiveData<List<Movie>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = FilmsApiImpl.getListOfFilms()
        }
    }
}