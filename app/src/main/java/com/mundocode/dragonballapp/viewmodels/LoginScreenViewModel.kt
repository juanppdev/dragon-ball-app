package com.mundocode.dragonballapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.mundocode.dragonballapp.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val auth:FirebaseAuth
): ViewModel() {

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

    @OptIn(ExperimentalSerializationApi::class)
    fun signOut(navController: NavController) {
        auth.signOut()
        GoogleSignIn.getClient(navController.context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut()
        navController.kiwiNavigation(Destinations.Login)
    }

}