package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.DragonsLista
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.SingleDragonBallZLista
import com.mundocode.dragonball.models.SingleDragonsLista
import com.mundocode.dragonballapp.data.Favorite
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
                async {
                    firebaseRepository.getAllFavorites().observeForever { favorites ->
                        _state.update {
                            it.copy(favoriteList = favorites)
                        }
                    }
                },
                async {
                    firebaseRepository.addFavorite(favorite = Favorite())
                },
                async {
                    firebaseRepository.removeFavorite(favorite = Favorite())
                },
            )
        }
    }



    private val _details = MutableStateFlow<SingleDragonBallLista?>(null) // Ajusta el tipo según sea necesario
    val details: StateFlow<SingleDragonBallLista?> get() = _details.asStateFlow()

    private val _detailsZ = MutableStateFlow<SingleDragonBallZLista?>(null) // Ajusta el tipo según sea necesario
    val detailsZ: StateFlow<SingleDragonBallZLista?> get() = _detailsZ.asStateFlow()

    private val _detailsD = MutableStateFlow<SingleDragonsLista?>(null) // Ajusta el tipo según sea necesario
    val detailsD: StateFlow<SingleDragonsLista?> get() = _detailsD.asStateFlow()

    fun getDetails(type: DragonBallType, id: Long) {
        viewModelScope.launch {
            val response = apiRepository.getDetails(type, id)
            if (response.isSuccessful) {
                when (type) {
                    DragonBallType.SAIYAN -> _details.value = response.body() as? SingleDragonBallLista
                    DragonBallType.SAIYAN_Z -> _detailsZ.value = response.body() as? SingleDragonBallZLista
                    DragonBallType.DRAGONS -> _detailsD.value = response.body() as? SingleDragonsLista
                }
            } else {
                Log.d("DragonBall Details Error", response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }

    data class DragonBallState(
        val dragonBallList: List<DragonBallLista>,
        val dragonBallZList: List<DragonBallZLista>,
        val dragonList: List<DragonsLista>,
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
    SAIYAN,
    SAIYAN_Z,
    DRAGONS
}

