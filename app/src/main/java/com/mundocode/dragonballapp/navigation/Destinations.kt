package com.mundocode.dragonballapp.navigation

import com.kiwi.navigationcompose.typed.Destination
import com.mundocode.dragonballapp.models.local.DbCharacter
import com.mundocode.dragonballapp.models.types.DragonBallType
import kotlinx.serialization.Serializable

sealed interface Destinations : Destination {

    @Serializable
    data object Login : Destinations

    @Serializable
    data object Home : Destinations

    @Serializable
    data class CharacterList(val dragonBallType: DragonBallType) : Destinations

    @Serializable
    data class CharacterDetail(
        val dragonBallType: DragonBallType,
        val characterRemote: DbCharacter
    ) : Destinations

    @Serializable
    data object OptionsScreen : Destinations
}