package com.mundocode.dragonballapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.data.Favorite
import com.mundocode.dragonballapp.models.Character
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.ApiRepositoryImpl
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DragonBallViewModel(
    private val apiRepository: ApiRepository = ApiRepositoryImpl(),
    private val firebaseRepository: FirebaseRepository = FirebaseRepositoryImpl()
) : ViewModel() {


    private val _state = MutableStateFlow(DragonBallState())
    val state: StateFlow<DragonBallState> = _state.asStateFlow()

    private val _details = MutableStateFlow<Character?>(null)
    val details: StateFlow<Character?> get() = _details.asStateFlow()

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
                    apiRepository.getDragonList().getOrElse { emptyList() }.let { list ->
                        _state.update {
                            it.copy(dragonList = list)
                        }
                    }
                },
//                async {
//                    getFavoriteList()
//                },
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

    fun getDetails(type: DragonBallType, id: Long) = viewModelScope.launch {
        apiRepository.getDetails(type, id).getOrNull()?.let { character ->
            _details.value = character
        }
    }

    fun favoriteClicked(itemId: Long) = viewModelScope.launch {
        val isFavorite = _state.value.favoriteList.any { it.id == itemId }
        if (isFavorite) {
            firebaseRepository.removeFavorite(Favorite(itemId))
        } else {
            firebaseRepository.addFavorite(Favorite(itemId))
        }
        getFavoriteList()
    }

    data class DragonBallState(
        val dragonBallList: List<Character>,
        val dragonBallZList: List<Character>,
        val dragonList: List<Character>,
        val favoriteList: List<Favorite>
    ) {
        constructor() : this(
            dragonBallList = emptyList(),
            dragonBallZList = emptyList(),
            dragonList = emptyList(),
            favoriteList = emptyList()
        )
    }
}

// Enum para identificar el tipo de lista
enum class DragonBallType {
    DragonBall,
    DragonBallZ,
    Dragons
}

