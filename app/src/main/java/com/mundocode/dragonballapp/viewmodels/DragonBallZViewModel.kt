package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.DragonBallZModel
import com.mundocode.dragonball.models.SingleDragonBallZLista
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

interface DBListRepositoryInterface {
    suspend fun getSaiyanZList(): Response<DragonBallZModel>
}

class DBListRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DBListRepositoryInterface {
    override suspend fun getSaiyanZList(): Response<List<DragonBallZLista>> {
        return apiService.obtenerPersonajesZ()
    }
}

class DragonBallZListViewModel(
    private val repository: DBListRepositoryInterface = DBListRepository()
): ViewModel() {
    private val _saiyanList = MutableStateFlow<List<DragonBallZLista>?>(null)

    val saiyanZList: StateFlow<List<DragonBallZLista>?> get() = _saiyanList.asStateFlow()

    init {
        getSaiyanZList()
    }

    private fun getSaiyanZList() {
        viewModelScope.launch {
            val response = repository.getSaiyanZList()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    Log.d("Success", "${body.size}")
                    _saiyanList.value = body
                }
            } else {
                val error = response.errorBody()?.string()
                Log.d("Saiyan List Error", error ?: "Unknown error")
            }
        }
    }
}




interface DragonZDetailsRepositoryInterface {
    suspend fun obtenerPersonaje(id: String): Response<SingleDragonBallZLista>
}

class DragonZDetailsRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonZDetailsRepositoryInterface {
    override suspend fun obtenerPersonaje(id: String): Response<SingleDragonBallZLista> {
        return apiService.obtenerPersonajeZ(id)
    }
}



class MyViewModelZ(
    id: String,
    private val repository: DragonZDetailsRepositoryInterface = DragonZDetailsRepository()
) : ViewModel() {

    // Mutable States
    private val _dragonZDetails = MutableStateFlow<SingleDragonBallZLista?>(null)

    // States
    val dragonZDetails: StateFlow<SingleDragonBallZLista?> get() = _dragonZDetails.asStateFlow()

    init {
        fetchDetails(id)
    }


    private fun fetchDetails(id: String) {
// Start in another thread
        viewModelScope.launch {
// Loading state
            val result = repository.obtenerPersonaje(id)
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
                _dragonZDetails.value = data
                Log.i("Juan", data.toString())
            } else {
// Handle empty data
                Log.d("Got nothing", "Got data")
            }
        }
    }
}

class MyViewModelFactoryZ(
    private val id: String,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModelZ(id) as T
    }
}