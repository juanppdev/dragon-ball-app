package com.mundocode.dragonballapp.models.remote

import com.google.gson.annotations.SerializedName
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.local.Transformations

data class DbCharacterRemote(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("biography")
    val biography: String?,
    @SerializedName("genre")
    val genre: String?,
    @SerializedName("race")
    val race: String?,
    @SerializedName("planet")
    val planet: String?,
    @SerializedName("transformations")
    val transformations: List<TransformationsRemote>?
)

data class TransformationsRemote(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("description")
    val description: String?,
)

fun DbCharacterRemote.toLocal() = DbCharacter(
    id = id ?: -1,
    name = name ?: "",
    image = image ?: "",
    description = description ?: "",
    biography = biography ?: "",
    genre = genre ?: "",
    race = race ?: "",
    planet = planet ?: "",
    transformations = transformations?.map { it.toLocal() } ?: emptyList(),
    isFavorite = false,
)

fun TransformationsRemote.toLocal() = Transformations(
    id = id ?: -1,
    image = image ?: "",
    description = description ?: "",
)