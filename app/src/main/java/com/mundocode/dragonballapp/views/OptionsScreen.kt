package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.viewmodels.LoginScreenViewModel

@Composable
fun OptionsScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomTopBar(
                title = "Settings",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                },
            )
        },
        bottomBar = { CustomBottomAppBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

//            Text(
//                text = "Settings",
//                textAlign = TextAlign.Start,
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.fillMaxWidth().padding(8.dp)
//            )

            LogoutButton(
                modifier = Modifier
                    .padding(8.dp)
                    .height(60.dp)
                    .fillMaxWidth(),
                logoutClick = {
                    viewModel.signOut(navController)
                },
                darkModeClick = {

                }
            )


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