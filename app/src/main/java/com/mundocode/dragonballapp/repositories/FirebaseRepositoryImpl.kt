package com.mundocode.dragonballapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mundocode.dragonballapp.data.Favorite

class FirebaseRepositoryImpl : FirebaseRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val favoritesCollection =
        firestore.collection("users").document(currentUser?.uid ?: "").collection("favoriteCharacters")

    override fun addFavorite(favorite: Favorite) {
        favoritesCollection.document(favorite.id.toString()).set(favorite)
    }

    override fun removeFavorite(favorite: Favorite) {
        favoritesCollection.whereEqualTo("id", favorite.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    favoritesCollection.document(document.id).delete()
                }
            }
    }

    override fun getAllFavorites(): LiveData<List<Favorite>> {
        val favoritesLiveData = MutableLiveData<List<Favorite>>()
        favoritesCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val favorites = snapshot.toObjects(Favorite::class.java)
                favoritesLiveData.value = favorites
                Log.d("Favorites", "Favorites: $favorites")
            }
        }
        return favoritesLiveData
    }
}


interface FirebaseRepository {
    fun addFavorite(favorite: Favorite)
    fun removeFavorite(favorite: Favorite)
    fun getAllFavorites(): LiveData<List<Favorite>>
}