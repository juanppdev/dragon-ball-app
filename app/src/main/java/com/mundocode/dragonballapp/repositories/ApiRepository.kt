package com.mundocode.dragonballapp.repositories

import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiDragonBall
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

}

interface ApiRepository {
    suspend fun getDragonBallList(): Result<List<Personaje>>
    suspend fun getDragonBallZList(): Result<List<Personaje>>
    suspend fun getDragonBallGTList(): Result<List<Personaje>>
    suspend fun getDragonList(): Result<List<Personaje>>
}
