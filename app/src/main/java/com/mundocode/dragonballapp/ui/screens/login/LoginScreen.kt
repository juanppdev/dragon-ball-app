package com.mundocode.dragonballapp.ui.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.navigation.Destinations
import com.mundocode.dragonballapp.ui.components.CustomTopBar
import com.mundocode.dragonballapp.ui.theme.DragonBallAppTheme
import kotlinx.serialization.ExperimentalSerializationApi
import com.kiwi.navigationcompose.typed.navigate as kiwiNavigation

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current as Activity
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.kiwiNavigation(Destinations.Home)
        }
    }

    Scaffold(
        modifier = Modifier,
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomTopBar(
                title = "Iniciar sesion",
                actions = {}
            )
        }
    ) { paddingValues ->

        LoginContent(
            modifier = Modifier.padding(paddingValues),
            loginGoogleClicked = {
                viewModel.handleGoogleSignIn(context)
            }
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    loginGoogleClicked: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dragon),
            contentDescription = "Imagen del login"
        )

        Image(
            painter = painterResource(id = R.drawable.ic_logoball),
            contentDescription = "Imagen del login",
            modifier = Modifier
                .size(400.dp)
                .offset(y = (-55).dp) // Ajusta el valor de y según sea necesario
                .align(Alignment.BottomCenter)
        )

        Text(
            text = "App",
            color = colorResource(id = R.color.app),
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(R.font.saiyansans)),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-80).dp, x = (-60).dp) // Ajusta el valor de y según sea necesario
        )

        Box(
            modifier = Modifier
                .offset(y = (0).dp) // Ajusta el valor de y según sea necesario
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { loginGoogleClicked() },
                verticalAlignment = Alignment.CenterVertically, // Cambiado a CenterVertically
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Logo Google",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                )

                Text(
                    text = "Login con Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
//@PreviewLightDark
//@PreviewScreenSizes
//@PreviewFontScale
@Composable
private fun LoginContentPreview() {
    DragonBallAppTheme {
        LoginContent(loginGoogleClicked = {})
    }
}