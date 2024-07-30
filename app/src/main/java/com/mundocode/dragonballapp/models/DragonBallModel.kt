package com.mundocode.dragonball.models

import com.google.gson.annotations.SerializedName

typealias DragonBallModel = List<DragonBallLista>

data class DragonBallLista (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("planet")
    val planet: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("biography")
    val biography: String,
)

data class SingleDragonBallLista (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("planet")
    val planet: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("biography")
    val biography: String,
)