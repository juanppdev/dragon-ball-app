package com.mundocode.dragonballapp.network

import com.mundocode.dragonballapp.models.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("dragonball")
    suspend fun obtenerPersonajes(): List<Character>

    @GET("dragonball/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: Long
    ): Character

    @GET("dragonballz")
    suspend fun obtenerPersonajesZ(): List<Character>

    @GET("dragonballz/{id}")
    suspend fun obtenerPersonajeZ(
        @Path("id") id: Long
    ): Character

    @GET("dragons")
    suspend fun obtenerDragons(): List<Character>

    @GET("dragons/{id}")
    suspend fun obtenerDragons(
        @Path("id") id: Long
    ): Character
}