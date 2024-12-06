package com.mundocode.dragonballapp.repositories

import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.remote.toLocal
import com.mundocode.dragonballapp.models.types.DragonBallType
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.network.ApiDragonBall.Companion.DRAGONBALL_GT_PATH
import com.mundocode.dragonballapp.network.ApiDragonBall.Companion.DRAGONBALL_PATH
import com.mundocode.dragonballapp.network.ApiDragonBall.Companion.DRAGONBALL_SUPER_PATH
import com.mundocode.dragonballapp.network.ApiDragonBall.Companion.DRAGONBALL_Z_PATH
import com.mundocode.dragonballapp.network.ApiDragonBall.Companion.DRAGONS_PATH
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiDragonBall
) : ApiRepository {

    override suspend fun getCharacters(type: DragonBallType): Result<List<DbCharacter>> =
        runCatching {
            when (type) {
                DragonBallType.DragonBall -> DRAGONBALL_PATH
                DragonBallType.DragonBallZ -> DRAGONBALL_Z_PATH
                DragonBallType.DragonBallGT -> DRAGONBALL_GT_PATH
                DragonBallType.DragonBallSuper -> DRAGONBALL_SUPER_PATH
                DragonBallType.Dragons -> DRAGONS_PATH
            }.let { path ->
                apiService.getCharacters(path).map { it.toLocal().copy(type = type) }
            }
        }
}

interface ApiRepository {
    suspend fun getCharacters(type: DragonBallType): Result<List<DbCharacter>>
}
