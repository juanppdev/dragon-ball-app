package com.mundocode.dragonballapp.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.local.Favorite
import com.mundocode.dragonballapp.models.types.DragonBallType
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _backendState = MutableStateFlow(BackendState(favoriteList = emptyList()))
    val backendState: StateFlow<BackendState> = _backendState.asStateFlow()

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() = viewModelScope.launch {
        // Get the list of favorites from the firestore
        firebaseRepository.getAllFavorites { favorites ->
            // Get all the characters from the API
            getAllCharacters { allCharacters ->
                favorites.getOrNull()?.mapNotNull { favorite ->
                    // Find the favourite characters in the list of all characters
                    allCharacters.find { it.id == favorite.id }
                }?.let { favorites ->
                    // Update the state with the list of favourite characters
                    _backendState.update { old ->
                        old.copy(
                            favoriteList = favorites.map { it.copy(isFavorite = true) }
                        )
                    }
                }
            }
        }
    }

    private fun getAllCharacters(characters: (List<DbCharacter>) -> Unit) {
        viewModelScope.launch {
            awaitAll(
                async { repository.getCharacters(DragonBallType.DragonBall).getOrThrow() },
                async { repository.getCharacters(DragonBallType.DragonBallZ).getOrThrow() },
                async { repository.getCharacters(DragonBallType.DragonBallGT).getOrThrow() },
                async { repository.getCharacters(DragonBallType.DragonBallSuper).getOrThrow() },
                async { repository.getCharacters(DragonBallType.Dragons).getOrThrow() }
            ).flatten().let {
                characters(it)
            }
        }
    }

    // Remove a favorite character from the list
    fun removeFavorite(character: DbCharacter) {
        firebaseRepository.removeFavorite(
            Favorite(character.id, character.type!!)
        )
    }

    data class BackendState(
        val favoriteList: List<DbCharacter>
    )
}