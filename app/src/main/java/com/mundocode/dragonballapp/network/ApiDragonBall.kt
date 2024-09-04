package com.mundocode.dragonballapp.network

import com.mundocode.dragonballapp.models.Personaje
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("dragonball")
    suspend fun obtenerPersonajes(): List<Personaje>

    @GET("dragonball/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: Long
    ): Personaje

    @GET("dragonballz")
    suspend fun obtenerPersonajesZ(): List<Personaje>

    @GET("dragonballz/{id}")
    suspend fun obtenerPersonajeZ(
        @Path("id") id: Long
    ): Personaje

    @GET("dragons")
    suspend fun obtenerDragons(): List<Personaje>

    @GET("dragons/{id}")
    suspend fun obtenerDragons(
        @Path("id") id: Long
    ): Personaje
}