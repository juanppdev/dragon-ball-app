package com.mundocode.dragonballapp.network

import com.mundocode.dragonballapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitClient {
//
//    val retrofit: ApiDragonBall by lazy {
//        Retrofit
//            .Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiDragonBall::class.java)
//    }
//
//}