package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonsLista
import com.mundocode.dragonball.models.DragonsModel
import com.mundocode.dragonball.models.SingleDragonsLista
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

interface DragonsListRepositoryInterface {
    suspend fun getDragonsList(): Response<DragonsModel>
}

class DragonsListRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonsListRepositoryInterface {
    override suspend fun getDragonsList(): Response<List<DragonsLista>> {
        return apiService.obtenerDragons()
    }
}

class DragonsListViewModel(
    private val repository: DragonsListRepositoryInterface = DragonsListRepository()
): ViewModel() {
    private val _dragonsList = MutableStateFlow<List<DragonsLista>?>(null)

    val dragonsList: StateFlow<List<DragonsLista>?> get() = _dragonsList.asStateFlow()

    init {
        getDragonsList()
    }

    private fun getDragonsList() {
        viewModelScope.launch {
            val response = repository.getDragonsList()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    Log.d("Success", "${body.size}")
                    _dragonsList.value = body
                }
            } else {
                val error = response.errorBody()?.string()
                Log.d("Saiyan List Error", error ?: "Unknown error")
            }
        }
    }
}




interface DragonsDetailsRepositoryInterface {
    suspend fun obtenerDragons(id: Long): Response<SingleDragonsLista>
}

class DragonsDetailsRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonsDetailsRepositoryInterface {
    override suspend fun obtenerDragons(id: Long): Response<SingleDragonsLista> {
        return apiService.obtenerDragons(id)
    }
}



class MyViewModelDragons(
    id: Long,
    private val repository: DragonsDetailsRepositoryInterface = DragonsDetailsRepository()
) : ViewModel() {

    // Mutable States
    private val _dragonsDetails = MutableStateFlow<SingleDragonsLista?>(null)

    // States
    val dragonsDetails: StateFlow<SingleDragonsLista?> get() = _dragonsDetails.asStateFlow()

    init {
        fetchDetails(id)
    }


    private fun fetchDetails(id: Long) {
// Start in another thread
        viewModelScope.launch {
// Loading state
            val result = repository.obtenerDragons(id)
            val error = result.errorBody()
            val data = result.body()

            if (error != null || !result.isSuccessful) {
// Handle error state
                Log.e("Got an error", "Got an error")
                return@launch
            }
            if (data != null) {
// Handle success case
                Log.i("Got data", "Got data")
                _dragonsDetails.value = data
                Log.i("Juan", data.toString())
            } else {
// Handle empty data
                Log.d("Got nothing", "Got data")
            }
        }
    }
}

class MyViewModelFactorydragons(
    private val id: Long,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModelDragons(id) as T
    }
}