package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallModel
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

interface DBZListRepositoryInterface {
    suspend fun getSaiyanList(): Response<DragonBallModel>
}

class DBZListRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DBZListRepositoryInterface {
    override suspend fun getSaiyanList(): Response<List<DragonBallLista>> {
        return apiService.obtenerPersonajes()
    }
}

@HiltViewModel
class DragonBallListViewModel @Inject constructor(
    private val repository: DBZListRepositoryInterface
): ViewModel() {
    private val _saiyanList = MutableStateFlow<List<DragonBallLista>?>(null)

    val saiyanList: StateFlow<List<DragonBallLista>?> get() = _saiyanList.asStateFlow()

    init {
        getSaiyanList()
    }

    private fun getSaiyanList() {
        viewModelScope.launch {
            val response = repository.getSaiyanList()
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




interface DragonDetailsRepositoryInterface {
    suspend fun obtenerPersonaje(id: String): Response<SingleDragonBallLista>
}

class DragonDetailsRepository(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
): DragonDetailsRepositoryInterface {
    override suspend fun obtenerPersonaje(id: String): Response<SingleDragonBallLista> {
        return apiService.obtenerPersonaje(id)
    }
}



class MyViewModel(
    id: String,
    private val repository: DragonDetailsRepositoryInterface = DragonDetailsRepository()
) : ViewModel() {

    // Mutable States
    private val _dragonDetails = MutableStateFlow<SingleDragonBallLista?>(null)

    // States
    val dragonDetails: StateFlow<SingleDragonBallLista?> get() = _dragonDetails.asStateFlow()

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
                _dragonDetails.value = data
                Log.i("Juan", data.toString())
            } else {
// Handle empty data
                Log.d("Got nothing", "Got data")
            }
        }
    }
}

class MyViewModelFactory(
    private val id: String,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(id) as T
    }
}