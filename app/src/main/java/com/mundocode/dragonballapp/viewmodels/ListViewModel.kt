package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.DragonsLista
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.SingleDragonBallZLista
import com.mundocode.dragonball.models.SingleDragonsLista
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

// Enum para identificar el tipo de lista
enum class DragonBallType {
    SAIYAN,
    SAIYAN_Z,
    DRAGONS
}

// Define los tipos específicos en el Repositorio
interface DragonBallRepositoryInterface {
    suspend fun getList(type: DragonBallType): Response<List<Any>>
    suspend fun getDetails(type: DragonBallType, id: Long): Response<Any>
}

class DragonBallRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
) : DragonBallRepositoryInterface {
    override suspend fun getList(type: DragonBallType): Response<List<Any>> {
        return when (type) {
            DragonBallType.SAIYAN -> apiService.obtenerPersonajes() as Response<List<Any>>
            DragonBallType.SAIYAN_Z -> apiService.obtenerPersonajesZ() as Response<List<Any>>
            DragonBallType.DRAGONS -> apiService.obtenerDragons() as Response<List<Any>>
        }
    }

    override suspend fun getDetails(type: DragonBallType, id: Long): Response<Any> {
        return when (type) {
            DragonBallType.SAIYAN -> apiService.obtenerPersonaje(id) as Response<Any>
            DragonBallType.SAIYAN_Z -> apiService.obtenerPersonajeZ(id) as Response<Any>
            DragonBallType.DRAGONS -> apiService.obtenerDragons(id) as Response<Any>
        }
    }
}


class UnifiedDragonBallViewModel(
    private val repository: DragonBallRepositoryInterface = DragonBallRepository()
) : ViewModel() {

    // Estado Flow
    private val _list = MutableStateFlow<List<DragonBallLista>?>(null)
    private val _listZ = MutableStateFlow<List<DragonBallZLista>?>(null)
    private val _listD = MutableStateFlow<List<DragonsLista>?>(null)

    val list: StateFlow<List<DragonBallLista>?> get() = _list.asStateFlow()
    val listZ: StateFlow<List<DragonBallZLista>?> get() = _listZ.asStateFlow()
    val listD: StateFlow<List<DragonsLista>?> get() = _listD.asStateFlow()

    init {
        // Cargar datos iniciales
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Llama a getList() para cada tipo
            getList(DragonBallType.SAIYAN)
            getList(DragonBallType.SAIYAN_Z)
            getList(DragonBallType.DRAGONS)
        }
    }

    fun getList(type: DragonBallType) {
        viewModelScope.launch {
            val response = repository.getList(type)
            if (response.isSuccessful) {
                when (type) {
                    DragonBallType.SAIYAN -> _list.value = response.body() as List<DragonBallLista>
                    DragonBallType.SAIYAN_Z -> _listZ.value = response.body() as List<DragonBallZLista>
                    DragonBallType.DRAGONS -> _listD.value = response.body() as List<DragonsLista>
                }
            } else {
                Log.d("DragonBall List Error", response.errorBody()?.string() ?: "Unknown error")
            }
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
            val response = repository.getDetails(type, id)
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

}



class UnifiedDragonBallViewModelFactory(
    private val type: DragonBallType,
    private val id: Long? = null
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnifiedDragonBallViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnifiedDragonBallViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
