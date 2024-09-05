package com.mundocode.dragonballapp.navigation

import com.kiwi.navigationcompose.typed.Destination
import com.mundocode.dragonballapp.models.Personaje
import com.mundocode.dragonballapp.viewmodels.DragonBallType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed interface Destinations : Destination {

    @Serializable
    data object Login : Destinations

    @Serializable
    data object Home : Destinations

    @Serializable
    data class PersonajeList(val dragonBallType: DragonBallType) : Destinations

    @Serializable
    data class PersonajeDetail(
        val dragonBallType: DragonBallType,
        val personaje: Personaje
    ) : Destinations

    @Serializable
    data object FavoriteScreen : Destinations
}