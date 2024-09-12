package com.mundocode.dragonballapp.repositories

import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import com.mundocode.dragonballapp.viewmodels.DragonBallType

class ApiRepositoryImpl(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
) : ApiRepository {

    override suspend fun getDragonBallList(): Result<List<Personaje>> = runCatching {
        apiService.obtenerPersonajes()
    }

    override suspend fun getDragonBallZList(): Result<List<Personaje>> = runCatching {
        apiService.obtenerPersonajesZ()
    }

    override suspend fun getDragonBallGTList(): Result<List<Personaje>> = runCatching {
        apiService.obtenerPersonajesGT()
    }

    override suspend fun getDragonList(): Result<List<Personaje>> = runCatching {
        apiService.obtenerDragons()
    }

    override suspend fun getDetails(type: DragonBallType, id: Long): Result<Personaje> =
        runCatching {
            when (type) {
                DragonBallType.DragonBall -> apiService.obtenerPersonaje(id)
                DragonBallType.DragonBallZ -> apiService.obtenerPersonajeZ(id)
                DragonBallType.DragonBallGT -> apiService.obtenerPersonajeGT(id)
                DragonBallType.Dragons -> apiService.obtenerDragons(id)
            }
        }
}

interface ApiRepository {
    suspend fun getDragonBallList(): Result<List<Personaje>>
    suspend fun getDragonBallZList(): Result<List<Personaje>>
    suspend fun getDragonBallGTList(): Result<List<Personaje>>
    suspend fun getDragonList(): Result<List<Personaje>>
    suspend fun getDetails(type: DragonBallType, id: Long): Result<Personaje>
}
