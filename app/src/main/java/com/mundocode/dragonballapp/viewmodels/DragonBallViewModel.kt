package com.mundocode.dragonballapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.ApiRepositoryImpl
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepositoryImpl
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

//    private val _details = MutableStateFlow<Personaje?>(null)
//    val details: StateFlow<Personaje?> get() = _details.asStateFlow()

    init {
        viewModelScope.launch {
            awaitAll(
                async {
                    apiRepository.getDragonBallList().getOrElse { emptyList() }.let { list ->
                        _state.update {
                            it.copy(dragonBallList = list)
                        }
                    }
                },
                async {
                    apiRepository.getDragonBallZList().getOrElse { emptyList() }.let { list ->
                        _state.update {
                            it.copy(dragonBallZList = list)
                        }
                    }
                },
                async {
                    apiRepository.getDragonBallGTList().getOrElse { emptyList() }.let { list ->
                        _state.update {
                            it.copy(dragonBallGtList = list)
                        }
                    }
                },
                async {
                    apiRepository.getDragonList().getOrElse { emptyList() }.let { list ->
                        _state.update {
                            it.copy(dragonList = list)
                        }
                    }
                },
                async {
                    getFavoriteList()
                },
            )
        }
    }

    private fun getFavoriteList() {
        firebaseRepository.getAllFavorites { favorites ->
            _state.update {
                it.copy(favoriteList = favorites)
            }
        }
    }

    fun favoriteClicked(itemId: Long, character: DragonBallType) = viewModelScope.launch {
        val isFavorite = _state.value.favoriteList.any { it.id == itemId }
        if (isFavorite) {
            firebaseRepository.removeFavorite(Favorite(itemId, character))
        } else {
            firebaseRepository.addFavorite(Favorite(itemId, character))
        }
        getFavoriteList()
    }

    data class DragonBallState(
        val dragonBallList: List<Personaje>,
        val dragonBallZList: List<Personaje>,
        val dragonBallGtList: List<Personaje>,
        val dragonList: List<Personaje>,
        val favoriteList: List<Favorite>
    ) {
        constructor() : this(
            dragonBallList = emptyList(),
            dragonBallZList = emptyList(),
            dragonBallGtList = emptyList(),
            dragonList = emptyList(),
            favoriteList = emptyList()
        )
    }
}

// Enum para identificar el tipo de lista
enum class DragonBallType {
    DragonBall,
    DragonBallZ,
    DragonBallGT,
    Dragons
}

