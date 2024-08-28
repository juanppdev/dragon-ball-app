package com.mundocode.dragonballapp.views

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.ui.theme.DragonBallAppTheme
import com.mundocode.dragonballapp.viewmodels.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential) {
                navController.navigate("homeScreen")
            }
        } catch (ex: Exception) {
            Log.d("Juan", "Error: ${ex.localizedMessage}")
        }
    }

    Scaffold(
        modifier = Modifier,
        contentColor = Color.White,
        containerColor = colorResource(id = R.color.background),
        topBar = {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.card),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        "Iniciar sesion",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->

        LoginContent(
            modifier = Modifier.padding(paddingValues),
            loginGoogleClicked = {
                val opciones = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(navController.context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient =
                    GoogleSignIn.getClient(navController.context, opciones)
                launcher.launch(googleSignInClient.signInIntent)
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