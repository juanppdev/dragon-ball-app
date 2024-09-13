package com.mundocode.dragonballapp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mundocode.dragonballapp.network.ApiDragonBall
import com.mundocode.dragonballapp.repositories.ApiRepository
import com.mundocode.dragonballapp.repositories.ApiRepositoryImpl
import com.mundocode.dragonballapp.repositories.FirebaseRepository
import com.mundocode.dragonballapp.repositories.FirebaseRepositoryImpl
import com.mundocode.dragonballapp.repositories.LocalRepository
import com.mundocode.dragonballapp.repositories.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesApiRepository(apiService: ApiDragonBall): ApiRepository {
        return ApiRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesFirebaseRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(firestore, auth)
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesLocalRepository(@ApplicationContext context: Context): LocalRepository {
        return LocalRepositoryImpl(context)
    }
}
