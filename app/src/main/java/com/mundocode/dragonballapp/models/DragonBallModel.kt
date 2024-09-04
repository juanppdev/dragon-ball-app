package com.mundocode.dragonballapp.models

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("genre")
    val genre: String?,
    @SerializedName("race")
    val race: String?,
    @SerializedName("planet")
    val planet: String?,
//    @SerializedName("transformations")
//    val transformations: List<Transformations> ?
)

data class Transformations(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("description")
    val description: String?,
)


