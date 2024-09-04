package com.mundocode.dragonballapp.repositories

import com.mundocode.dragonballapp.models.Character
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.RetrofitClient
import com.mundocode.dragonballapp.viewmodels.DragonBallType

class ApiRepositoryImpl(
    private val apiService: ApiDragonBall = RetrofitClient.retrofit
) : ApiRepository {

    override suspend fun getDragonBallList(): Result<List<Character>> = runCatching {
        apiService.obtenerPersonajes()
    }

    override suspend fun getDragonBallZList(): Result<List<Character>> = runCatching {
        apiService.obtenerPersonajesZ()
    }

    override suspend fun getDragonList(): Result<List<Character>> = runCatching {
        apiService.obtenerDragons()
    }

    override suspend fun getDetails(type: DragonBallType, id: Long): Result<Character> =
        runCatching {
            when (type) {
                DragonBallType.DragonBall -> apiService.obtenerPersonaje(id)
                DragonBallType.DragonBallZ -> apiService.obtenerPersonajeZ(id)
                DragonBallType.Dragons -> apiService.obtenerDragons(id)
            }
        }
}

interface ApiRepository {
    suspend fun getDragonBallList(): Result<List<Character>>
    suspend fun getDragonBallZList(): Result<List<Character>>
    suspend fun getDragonList(): Result<List<Character>>
    suspend fun getDetails(type: DragonBallType, id: Long): Result<Character>
}
