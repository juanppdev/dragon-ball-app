package com.mundocode.dragonballapp.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.models.local.DbCharacter
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
class DragonBallViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DragonBallState())
    val state: StateFlow<DragonBallState> = _state.asStateFlow()

    fun getCharacterList(dragonBallType: DragonBallType) = viewModelScope.launch {

        _state.update {
            it.copy(isLoading = true)
        }

        var tempCharacterList = emptyList<DbCharacter>()
        var tempFavList = emptyList<DbCharacter>()

        awaitAll(
            async {
                when (dragonBallType) {
                    DragonBallType.DragonBall,
                    DragonBallType.DragonBallZ,
                    DragonBallType.DragonBallGT,
                    DragonBallType.DragonBallSuper,
                    DragonBallType.Dragons -> {
                        tempCharacterList = apiRepository.getCharacters(dragonBallType).getOrElse { error ->
                            _state.update {
                                it.copy(error = error.message)
                            }
                            emptyList()
                        }
                    }

                    DragonBallType.Favorites -> {
                        /* no-op */
                    }
                }
            },
            async {
                firebaseRepository.getAllFavorites { favorites ->
                    tempFavList = favorites.getOrElse { error ->
                        _state.update {
                            it.copy(error = error.message)
                        }
                        emptyList()
                    }
                }
            },
        )

        // Check if the selected DragonBallType is Favorites
        if (dragonBallType == DragonBallType.Favorites) {
            // If it is, use the favorite list
            tempFavList
        } else {
            // Otherwise, map the character list and set the isFavorite flag
            tempCharacterList.map { character ->
                character.copy(isFavorite = tempFavList.any { fav -> fav.id == character.id })
            }
        }.let { updatedList ->
            // Update the state with the new character list
            _state.update {
                it.copy(characterList = updatedList)
            }
        }

        _state.update {
            it.copy(isLoading = false)
        }
    }

    fun favoriteClicked(item: DbCharacter, dragonBallType: DragonBallType) = viewModelScope.launch {
        if (item.isFavorite) {
            firebaseRepository.removeFavorite(item)
        } else {
            firebaseRepository.addFavorite(item.copy(isFavorite = true))
        }
        getCharacterList(dragonBallType)
    }

    data class DragonBallState(
        val characterList: List<DbCharacter> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}
