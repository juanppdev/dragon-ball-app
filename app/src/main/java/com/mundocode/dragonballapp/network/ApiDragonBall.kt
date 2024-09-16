package com.mundocode.dragonballapp.network

import com.mundocode.dragonballapp.models.remote.DbCharacterRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("{type}")
    suspend fun getCharacters(
        @Path("type") type: String
    ): List<DbCharacterRemote>

//    @GET("dragonball")
//    suspend fun obtenerPersonajes(): List<Personaje>

    @GET("dragonball/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: Long
    ): DbCharacterRemote

//    @GET("dragonballz")
//    suspend fun obtenerPersonajesZ(): List<Personaje>

    @GET("dragonballz/{id}")
    suspend fun obtenerPersonajeZ(
        @Path("id") id: Long
    ): DbCharacterRemote

//    @GET("dragonballgt")
//    suspend fun obtenerPersonajesGT(): List<Personaje>

    @GET("dragonballgt/{id}")
    suspend fun obtenerPersonajeGT(
        @Path("id") id: Long
    ): DbCharacterRemote


    @GET("dragonballsuper/{id}")
    suspend fun obtenerPersonajeSUPER(
        @Path("id") id: Long
    ): DbCharacterRemote


//    @GET("dragons")
//    suspend fun obtenerDragons(): List<Personaje>

    @GET("dragons/{id}")
    suspend fun obtenerDragons(
        @Path("id") id: Long
    ): DbCharacterRemote

    companion object {
        const val DRAGONBALL_PATH = "dragonball"
        const val DRAGONBALL_Z_PATH = "dragonballz"
        const val DRAGONBALL_GT_PATH = "dragonballgt"
        const val DRAGONBALL_SUPER_PATH = "dragonballsuper"
        const val DRAGONS_PATH = "dragons"
    }

}