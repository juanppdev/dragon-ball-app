package com.mundocode.dragonball.network

import com.mundocode.dragonball.models.DragonBallLista
import com.mundocode.dragonball.models.SingleDragonBallLista
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

}