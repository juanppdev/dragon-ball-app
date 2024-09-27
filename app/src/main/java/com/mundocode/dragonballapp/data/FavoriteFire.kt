package com.mundocode.dragonballapp.data

import com.mundocode.dragonballapp.models.types.DragonBallType

data class Favorite(
    val id: Long = 0,
    val type: DragonBallType = DragonBallType.DragonBall // Valor por defecto para cumplir con la deserialización
) {
    // Constructor sin argumentos requerido por Firestore
    constructor() : this(id = 0, type = DragonBallType.DragonBall)
}