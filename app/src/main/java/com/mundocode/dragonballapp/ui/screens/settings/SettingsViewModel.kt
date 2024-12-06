package com.mundocode.dragonballapp.ui.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mundocode.dragonballapp.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val localRepository: LocalRepository,
) : ViewModel() {

    val isDarkMode = localRepository.darkModeFlow.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = false,
    )

    fun signOut(context: Context) {
        auth.signOut()
        GoogleSignIn.getClient(context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
            .signOut()
    }

    fun toggleDarkMode(darkMode: Boolean) = viewModelScope.launch {
        localRepository.setDarkMode(darkMode)
    }

}
