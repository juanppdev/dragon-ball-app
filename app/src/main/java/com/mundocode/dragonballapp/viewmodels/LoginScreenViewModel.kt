package com.mundocode.dragonballapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.mundocode.dragonballapp.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) = viewModelScope.launch {

        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Juan", "Logueado con Google")
                        home()
                    }
                }
                .addOnFailureListener {
                    Log.d("Juan", "Fallo al loguear con Google")
                }
        } catch (ex: Exception) {
            Log.d("Juan", "Error: ${ex.localizedMessage}")
        }
    }
}
