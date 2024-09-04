package com.mundocode.dragonballapp.network

import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.DragonBallZLista
import com.mundocode.dragonball.models.DragonsLista
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonball.models.SingleDragonBallZLista
import com.mundocode.dragonball.models.SingleDragonsLista
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDragonBall {

    @GET("dragonball")
    suspend fun obtenerPersonajes(): List<DragonBallLista>

    @GET("dragonball/{id}")
    suspend fun obtenerPersonaje(
        @Path("id") id: Long
    ): Response<SingleDragonBallLista>

    @GET("dragonballz")
    suspend fun obtenerPersonajesZ(): List<DragonBallZLista>

    @GET("dragonballz/{id}")
    suspend fun obtenerPersonajeZ(
        @Path("id") id: Long
    ): Response<SingleDragonBallZLista>

    @GET("dragons")
    suspend fun obtenerDragons(): List<DragonsLista>

    @GET("dragons/{id}")
    suspend fun obtenerDragons(
        @Path("id") id: Long
    ): Response<SingleDragonsLista>

}