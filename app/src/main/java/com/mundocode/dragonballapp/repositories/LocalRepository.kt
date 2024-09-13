package com.mundocode.dragonballapp.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mundocode.dragonballapp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    context: Context
) : LocalRepository {

    private val Context.dataStore by preferencesDataStore(name = LOCAL_REPOSITORY_NAME)

    private val dataStore = context.dataStore

    override val darkModeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    companion object {
        private const val LOCAL_REPOSITORY_NAME = "${BuildConfig.APPLICATION_ID}.settings"
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }
}

interface LocalRepository {
    val darkModeFlow: Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
}
