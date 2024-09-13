package com.mundocode.dragonballapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    private val _favoriteState = MutableStateFlow(FavoriteState(favoriteList = emptyList()))
    val favoriteState: StateFlow<FavoriteState> = _favoriteState.asStateFlow()

    init {
        firebaseRepository.getAllFavorites { favorites ->
            _favoriteState.value = _favoriteState.value.copy(favoriteList = favorites)
        }
    }

    fun addFavorite(favorite: Favorite) {
        firebaseRepository.addFavorite(favorite)
    }

    fun removeFavorite(favorite: Favorite) {
        firebaseRepository.removeFavorite(favorite)
    }

    fun getAllFavorites() {
        firebaseRepository.getAllFavorites { favorites ->
            _favoriteState.value = _favoriteState.value.copy(favoriteList = favorites)
        }
    }

    data class FavoriteState(
        val favoriteList: List<Favorite>
    )
}