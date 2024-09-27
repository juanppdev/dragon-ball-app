package com.mundocode.dragonballapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.models.local.Favorite
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.types.DragonBallType
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonBallViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val firebaseRepository: FirebaseRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DragonBallState())
    val state: StateFlow<DragonBallState> = _state.asStateFlow()

    fun getCharacterList(dragonBallType: DragonBallType) {

        _state.update {
            it.copy(isLoading = true)
        }

        firebaseRepository.getAllFavorites { favoriteList ->
            val favorites = favoriteList.getOrNull() ?: emptyList()

            when (dragonBallType) {
                DragonBallType.DragonBall,
                DragonBallType.DragonBallZ,
                DragonBallType.DragonBallGT,
                DragonBallType.DragonBallSuper,
                DragonBallType.Dragons -> {
//                    viewModelScope.launch {
//                        apiRepository.getCharacters(dragonBallType).getOrElse { error ->
//                            _state.update {
//                                it.copy(error = error.message)
//                            }
//                            emptyList()
//                        }.let { characters ->
//                            // Get the list of characters from the API
//                            characters.map { character ->
//                                // Map the character list and set the isFavorite flag
//                                character.copy(isFavorite = character.id in favorites.map { it.id })
//                            }.let{
//                                // Update the state with the list of characters
//                                _state.update {
//                                    it.copy(characterList =  characters)
//                                }
//                            }
//                        }
//                    }


                    viewModelScope.launch {
                        apiRepository.getCharacters(dragonBallType).getOrElse { error ->
                            _state.update {
                                it.copy(error = error.message)
                            }
                            emptyList()
                        }.map { character ->
                            // Get the list of characters from the API
                            // Map the character list and set the isFavorite flag
                            character.copy(isFavorite = character.id in favorites.map { it.id })
                        }.let { characters ->
                            // Update the state with the list of characters
                            _state.update {
                                it.copy(characterList = characters)
                            }
                        }
                    }
                }
            }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun favoriteClicked(item: DbCharacter, dragonBallType: DragonBallType) =
        viewModelScope.launch {
            if (item.isFavorite) {
                firebaseRepository.removeFavorite(
                    Favorite(item.id, item.type!!)
                )
            } else {
                firebaseRepository.addFavorite(
                    Favorite(item.id, item.type!!)
                )
            }
            getCharacterList(dragonBallType)
        }

    data class DragonBallState(
        val characterList: List<DbCharacter> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}
