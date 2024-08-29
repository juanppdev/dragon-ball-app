package com.mundocode.dragonballapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepositoryImpl

class FavoriteViewModel : ViewModel() {

    private val repository : FirebaseRepository= FirebaseRepositoryImpl()

    val allFavorites: LiveData<List<Favorite>> = repository.getAllFavorites()

    fun addFavorite(favorite: Favorite) {
        repository.addFavorite(favorite)
    }

    fun removeFavorite(favorite: Favorite) {
        repository.removeFavorite(favorite)
    }
}
