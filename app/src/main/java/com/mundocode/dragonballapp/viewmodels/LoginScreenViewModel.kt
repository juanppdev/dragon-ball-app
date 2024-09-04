package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

    private val auth:FirebaseAuth = Firebase.auth

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) = viewModelScope.launch {

        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.d("Juan", "Logueado con Google")
                        home()
                    }
                }
                .addOnFailureListener {
                    Log.d("Juan", "Fallo al loguear con Google")
                }
        }
        catch (ex:Exception) {
            Log.d("Juan", "Error: ${ex.localizedMessage}")
        }
    }
}