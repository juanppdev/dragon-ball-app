package com.mundocode.dragonballapp.network

import com.mundocode.dragonballapp.models.remote.DbCharacterRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("{type}")
    suspend fun getCharacters(
        @Path("type") type: String
    ): List<DbCharacterRemote>

    companion object {
        const val DRAGONBALL_PATH = "dragonball"
        const val DRAGONBALL_Z_PATH = "dragonballz"
        const val DRAGONBALL_GT_PATH = "dragonballgt"
        const val DRAGONBALL_SUPER_PATH = "dragonballsuper"
        const val DRAGONS_PATH = "dragons"
    }
}