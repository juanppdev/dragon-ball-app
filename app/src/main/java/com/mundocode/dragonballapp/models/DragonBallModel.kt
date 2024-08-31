package com.mundocode.dragonball.models

import com.google.gson.annotations.SerializedName

data class DragonBallLista (
    @SerializedName("id")
    val id: Long,
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
    val id: Long,
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

data class DragonBallZLista (
    @SerializedName("id")
    val id: Long,
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
    @SerializedName("transformations")
    val transformations: List<Transformations>
)

data class Transformations (
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
)

data class SingleDragonBallZLista (
    @SerializedName("id")
    val id: Long,
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
    @SerializedName("transformations")
    val transformations: List<Transformations>
)

data class DragonsLista (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("biography")
    val biography: String
)

data class SingleDragonsLista (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("biography")
    val biography: String
)