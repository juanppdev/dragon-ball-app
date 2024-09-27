package com.mundocode.dragonballapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun CustomBottomAppBar(
    navController: NavController,
    selectedItem: Destinations? = null,
) {

    BottomAppBar {
        NavigationBar {
            CustomNavigationBarItem(
                selected = selectedItem == Destinations.Home,
                onClick = { navController.kiwiNavigation(Destinations.Home) },
                icon = R.drawable.wiki,
                label = "Home"
            )
            CustomNavigationBarItem(
                selected = false, // FIXME: update when game screen is created
                onClick = { },
                icon = R.drawable.games,
                label = "Games",
                enabled = false
            )
            CustomNavigationBarItem(
                selected = selectedItem == Destinations.OptionsScreen,
                onClick = { navController.kiwiNavigation(Destinations.OptionsScreen) },
                icon = R.drawable.ic_settings,
                label = "Settings"
            )
        }
    }
}

@Composable
fun RowScope.CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painterResource(id = icon),
                contentDescription = null,
                modifier = modifier.size(30.dp),
            )
        },
        label = {
            Text(text = label)
        },
        enabled = enabled,
        // TODO: Modify these colours with the desired ones
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun CustomBottomAppBarPreview(
    @PreviewParameter(CustomBottomAppBarParams::class) selectedItem: Destinations
) {
    CustomBottomAppBar(
        navController = rememberNavController(),
        selectedItem = selectedItem
    )
}

private class CustomBottomAppBarParams : PreviewParameterProvider<Destinations> {
    override val values: Sequence<Destinations> = sequenceOf(
        Destinations.Home,
        Destinations.OptionsScreen
    )
}