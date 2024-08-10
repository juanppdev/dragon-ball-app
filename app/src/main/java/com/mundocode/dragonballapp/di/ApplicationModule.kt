package com.mundocode.dragonballapp.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mundocode.dragonballapp.viewmodels.DBZListRepository
import com.mundocode.dragonballapp.viewmodels.DBZListRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() =  Firebase.auth
}

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    fun provideDBZListRepository(): DBZListRepositoryInterface = DBZListRepository()
}
