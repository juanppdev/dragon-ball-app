package com.mundocode.dragonballapp.models.local

import com.mundocode.dragonballapp.models.types.DragonBallType
import kotlinx.serialization.Serializable

@Serializable
data class DbCharacter(
    val id: Long,
    val type : DragonBallType?,
    val name: String,
    val image: String,
    val description: String,
    val biography: String,
    val genre: String,
    val race: String,
    val planet: String,
    val transformations: List<Transformations>,
    val isFavorite: Boolean,
){
    constructor() : this(
        id = -1,
        type = null,
        name = "",
        image = "",
        description = "",
        biography = "",
        genre = "",
        race = "",
        planet = "",
        transformations = emptyList(),
        isFavorite = false
    )
}

@Serializable
data class Transformations(
    val id: Long?,
    val image: String?,
    val description: String?,
){
    constructor() : this(
        id = -1,
        image = "",
        description = ""
    )
}
