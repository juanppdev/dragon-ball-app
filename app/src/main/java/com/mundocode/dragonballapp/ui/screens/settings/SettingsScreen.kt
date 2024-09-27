package com.mundocode.dragonballapp.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.components.CustomBottomAppBar
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val isDarkMode = viewModel.isDarkMode.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomTopBar(title = "Settings")
        },
        bottomBar = { CustomBottomAppBar(navController, Destinations.OptionsScreen) }
    ) { paddingValues ->

        SettingsContent(
            isDarkMode = isDarkMode.value,
            modifier = Modifier.padding(paddingValues),
            signOut = {
                viewModel.signOut(context)
                navController.kiwiNavigation(Destinations.Login)
            },
            toggleDarkMode = { darkMode ->
                viewModel.toggleDarkMode(darkMode)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    signOut: () -> Unit = {},
    toggleDarkMode: (Boolean) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {


        item {
            SettingsItem("Cerrar Sesión", R.drawable.ic_logout, action = signOut)
        }

        item {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                SettingsItem(
                    title = "Modo Oscuro",
                    icon = R.drawable.ic_dark,
                    additionalComponent = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = {
                                    toggleDarkMode(it)
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    LogoutButton(
        modifier = Modifier
            .padding(8.dp)
            .height(60.dp)
            .fillMaxWidth(),
        logoutClick = signOut,
        darkModeClick = {

        }
    )
}

@Composable
fun SettingsItem(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit = {},
    additionalComponent: @Composable () -> Unit = {}
) {

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { action() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = title,
                textAlign = TextAlign.Start,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            additionalComponent()
        }
    }
}


@Composable
fun LogoutButton(
    modifier: Modifier,
    logoutClick: () -> Unit,
    darkModeClick: () -> Unit
) {

    Card(
        modifier = modifier.clickable {
            logoutClick()
        },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Cerrar Sesión",
                textAlign = TextAlign.Start,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

    Card(
        modifier = modifier.clickable {
            darkModeClick()
        },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_dark),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Modo Oscuro",
                textAlign = TextAlign.Start,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = NavController(LocalContext.current))
}

@Preview
@Composable
fun SettingsContentPreview() {
    SettingsContent(isDarkMode = false)
}