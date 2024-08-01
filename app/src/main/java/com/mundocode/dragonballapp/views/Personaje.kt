package com.mundocode.dragonballapp.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mundocode.dragonball.models.SingleDragonBallLista
import com.mundocode.dragonballapp.R
import com.mundocode.dragonballapp.viewmodels.MyViewModel
import com.mundocode.dragonballapp.viewmodels.MyViewModelFactory
import com.mundocode.dragonballapp.viewmodels.MyViewModelFactoryZ

@Composable
fun Personaje(
    navController: NavController,
    id: String
) {

    val viewModel = viewModel<MyViewModel>(
        factory = MyViewModelFactory(id)
    )

    val dragonDetails by viewModel.dragonDetails.collectAsState()

    Content(
        dragonDetails = dragonDetails,
        navController = navController
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    dragonDetails: SingleDragonBallLista?,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.background),
                topBar = {
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
                        rememberTopAppBarState()
                    )

                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorResource(id = R.color.card),
                            titleContentColor = Color.White,
                        ),
                        title = {
                            Text(
                                "Personajes",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate("dragonBall") }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    tint = Color.White,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                    )
                },
                bottomBar = { BottomAppBar() }
            ) {

                dragonDetails.let { details ->
                    if (details != null) {

                        LazyColumn(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            item {

                                Box(
                                    modifier = Modifier
                                        .background(Color.Gray)
                                        .fillMaxWidth()
                                        .height(300.dp), contentAlignment = Alignment.Center
                                ) {

//                                    Canvas(modifier = Modifier.fillMaxSize().blur(70.dp)) {
//                                        scale(scaleX = 6f, scaleY = 6f) {
//                                            drawCircle(Color.Blue, radius = 20.dp.toPx())
//                                        }
//                                    }
                                    AsyncImagePerson(url = details.image, modifier = Modifier)
                                }
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = "Descripcion",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = details.description,
                                    textAlign = TextAlign.Start,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                                        .fillMaxWidth(),
                                    text = "Planeta al que pertence",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                            }
                        }

                    }
                }

            }
        }
}

@Composable
fun AsyncImagePerson(url: String, modifier: Modifier) {
    val painter: Painter = // Optionally, you can apply transformations
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    // Optionally, you can apply transformations
                    transformations()
                }).build()
        )
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = null
    )
}