package com.mundocode.dragonballapp.repositories

import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.DragonsLista
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import retrofit2.Response

class ApiRepositoryImpl(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
) : ApiRepository {

    override suspend fun getDragonBallList(): Result<List<DragonBallLista>> = runCatching {
        apiService.obtenerPersonajes()
    }

    override suspend fun getDragonBallZList(): Result<List<DragonBallZLista>> = runCatching {
        apiService.obtenerPersonajesZ()
    }

    override suspend fun getDragonList(): Result<List<DragonsLista>> = runCatching {
        apiService.obtenerDragons()
    }

    override suspend fun getDetails(type: DragonBallType, id: Long): Response<Any> {
        return when (type) {
            DragonBallType.SAIYAN -> apiService.obtenerPersonaje(id) as Response<Any>
            DragonBallType.SAIYAN_Z -> apiService.obtenerPersonajeZ(id) as Response<Any>
            DragonBallType.DRAGONS -> apiService.obtenerDragons(id) as Response<Any>
        }
    }
}

interface ApiRepository {
    //    suspend fun getList(type: DragonBallType): Response<List<Any>>
    suspend fun getDragonBallList(): Result<List<DragonBallLista>>
    suspend fun getDragonBallZList(): Result<List<DragonBallZLista>>
    suspend fun getDragonList(): Result<List<DragonsLista>>
    suspend fun getDetails(type: DragonBallType, id: Long): Response<Any>
}
