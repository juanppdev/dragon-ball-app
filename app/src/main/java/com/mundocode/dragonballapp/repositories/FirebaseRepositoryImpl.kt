package com.mundocode.dragonballapp.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mundocode.dragonballapp.models.local.DbCharacter
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore,
    auth: FirebaseAuth
) : FirebaseRepository {

    private val favoritesCollection = firestore.collection("users")
        .document(auth.currentUser?.uid ?: "")
        .collection("favoriteCharacters")

    override fun addFavorite(favorite: DbCharacter) {
        favoritesCollection.document(favorite.id.toString()).set(favorite)
    }

    override fun removeFavorite(favorite: DbCharacter) {
        favoritesCollection.whereEqualTo("id", favorite.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    favoritesCollection.document(document.id).delete()
                }
            }
    }

    override fun getAllFavorites(callback: (Result<List<DbCharacter>>) -> Unit) {
        favoritesCollection
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val favorites = snapshot.toObjects(DbCharacter::class.java)
                    //Log.d("Favorites", "Favorites: $favorites")
                    callback(Result.success(favorites))
                }else{
                    callback(Result.failure(Exception("Error getting favorites")))
                }
            }

    }
}

interface FirebaseRepository {
    fun addFavorite(favorite: DbCharacter)
    fun removeFavorite(favorite: DbCharacter)
    fun getAllFavorites(callback: (Result<List<DbCharacter>>) -> Unit)
}