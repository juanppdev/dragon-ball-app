package com.mundocode.dragonballapp.network

import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.SingleDragonBallZLista
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("dragonball")
    suspend fun obtenerPersonajes(): Response<List<DragonBallLista>>

    @GET("dragonball/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: String
    ): Response<SingleDragonBallLista>

    @GET("dragonballz")
    suspend fun obtenerPersonajesZ(): Response<List<DragonBallZLista>>

    @GET("dragonballz/{id}")
    suspend fun obtenerPersonajeZ(
        @Path("id") id: String
    ): Response<SingleDragonBallZLista>

}