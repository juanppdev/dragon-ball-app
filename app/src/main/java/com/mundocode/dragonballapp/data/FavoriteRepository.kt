package com.mundocode.dragonballapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val favoritesCollection = firestore.collection("users").document(currentUser?.uid ?: "").collection("favoriteCharacters")

    fun addFavorite(favorite: Favorite) {
        favoritesCollection.document(favorite.id.toString()).set(favorite)
    }

    fun removeFavorite(favorite: Favorite) {
        favoritesCollection.whereEqualTo("id", favorite.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    favoritesCollection.document(document.id).delete()
                }
            }
    }

    fun getAllFavorites(): LiveData<List<Favorite>> {
        val favoritesLiveData = MutableLiveData<List<Favorite>>()
        favoritesCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val favorites = snapshot.toObjects(Favorite::class.java)
                favoritesLiveData.value = favorites
            }
        }
        return favoritesLiveData
    }
}
