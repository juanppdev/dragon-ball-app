package com.mundocode.dragonballapp.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mundocode.dragonballapp.data.Favorite
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
    auth: FirebaseAuth
) : FirebaseRepository {

    private val favoritesCollection = firestore.collection("users")
        .document(auth.currentUser?.uid ?: "")
        .collection("favoriteCharacters")

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

    override fun getAllFavorites(callback: (List<Favorite>) -> Unit) {
        favoritesCollection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val favorites = snapshot.toObjects(Favorite::class.java)
                Log.d("Favorites", "Favorites: $favorites")
                callback(favorites)
            }
        }
    }
}

interface FirebaseRepository {
    fun addFavorite(favorite: Favorite)
    fun removeFavorite(favorite: Favorite)
    fun getAllFavorites(callback: (List<Favorite>) -> Unit)
}