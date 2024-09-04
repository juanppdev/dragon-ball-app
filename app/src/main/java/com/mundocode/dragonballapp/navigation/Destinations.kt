package com.mundocode.dragonballapp.navigation

import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Home

@Serializable
data class PersonajeList(val dragonBallType: DragonBallType)

@Serializable
data class PersonajeDetail(
    val dragonBallType: DragonBallType,
    val personaje: Personaje
)

