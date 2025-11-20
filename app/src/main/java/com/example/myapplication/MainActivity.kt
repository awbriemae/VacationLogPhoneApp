package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


// The screens have been moved to seperate places because the amount of lines was insane
// If the format looks weird that why
data class HolidayDestination(
    val name: String,
    val imageUrl: String? = null,
    val description: String = "A nice place."
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // navcontroller for navigating through screens
                val navController = rememberNavController()
                val viewModel: HolidayViewModel = viewModel()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        HolidayListScreen(navController)
                    }
                    composable("details/{name}/{imageUrl}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val imageUrl = backStackEntry.arguments?.getString("imageUrl")
                        DestinationDetailsScreen(
                            // This is the destination details thing
                            name = name,
                            imageUrl = imageUrl,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable("logs") {
                        LogsScreen(navController, viewModel)
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
// This is where the holiday list is stored
// After learning and working with kotlin/androidstudio more I shouldve made this a json instead
fun HolidayListScreen(navController: NavHostController) {
    // i chose polish location because I am polish and have strong opinions about some of these >:I
    val destinations = listOf(
        // All of these locations are the actual polish spelling not the englishised versions
        // All of these images were taken from the first image you get when you look up the location
        // Also these may display errors if the phone doesn't support these characters like "ł"
        HolidayDestination(
            "Warszawa",
            "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcRB60imyi9a_HIVT1N9nNmPjwB6WKB4s7nL1DvuELaG9j8MGlXj053Ri0k2nq3Qs-6q"
        ),
        HolidayDestination(
            "Gdańsk",
            "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcRoZiV6OwgStLazJX_UiJTL8a6EYuYowUmzhQ8XvrAfWgnHLL3_y0of2pYX4OR5bAIy"
        ),
        HolidayDestination(
            "Wrocław",
            "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQI2RfOyAOEK9WcMmT0fvZx0HAU0TvAEAOFkNvSvjuTiwkDKSVg32Q83pVr6bSjxgHe"
        ),
        HolidayDestination(
            "Poznań",
            "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcTUOPmO5OmMt8tgMrOcmQCmDwrD9SZ0NwEhpgzP6QHJyioPhH04teSiwgxLjxJnQL_r"
        ),
        HolidayDestination(
            "Katowice",
            "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcRKa7eO_cc9E5GA2dv2zX3Jprb2dzXhCbuJaUvES41nr0ELLRhhkGodbLUjwry4UvMu"
        ),
        HolidayDestination(
            "Łódź",
            "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcS-GipyxIpPzu4ewJrcPpuupjEZ7zPO4t5PCCe2yL2_NreG6KOx-TvknyLDsj3EGJYi"
        ),
        HolidayDestination(
            "Bydgoszcz",
            "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcTtW0Yi4aE387_nm7TiweUoJZDqQ-Lo3O-9nyiX1rZ0vNShTnceX7yjLIWawQfKGm1X"
        ),
        HolidayDestination(
            "Szczecin",
            "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcTqEJrmA-WbRyhO591yoSWcwWMjQy5RE4go9TRnEglaLMrN_XVShv6wmIdHeJmeJfpO"
        ),
        HolidayDestination(
            "Gdynia",
            "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcQBUArZ_nXtV9Gkpqi4E_q7hYnuhaSykkK_6GrUJAfYL9Qxm1u2KSMmGFZjTJAT3tnv"
        ),
        HolidayDestination(
            "Zakopane",
            "https://lh3.googleusercontent.com/gps-cs-s/AG0ilSwuFTl_uQnRRJZqxDTt8Lf-8e6qQruzzgyRI5Jkn788RpAXYBSLV37smQEx8IT6pqjM1Fto359RT7CGCwUUO8pxqmVPCSAD9hAmLofx7QT4xTHR5_Ob4-Ok3oNSu8q7kQdqgdLl=w540-h312-n-k-no"
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Poland Holiday Destinations") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Button(
                // Here is the buttons for logs
                onClick = { navController.navigate("logs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("View Logs")
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // here I had to do this because it would just throw out so many errors
                // i think its with the way that it reads the chars
                // once again stack overflow saved me :D
                items(destinations) { destination ->
                    val encodedName =
                        URLEncoder.encode(destination.name, StandardCharsets.UTF_8.toString())
                    val encodedUrl = URLEncoder.encode(
                        destination.imageUrl ?: "",
                        StandardCharsets.UTF_8.toString()
                    )

                    DestinationRow(destination) {
                        navController.navigate("details/$encodedName/$encodedUrl")
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------------------
// Screen 1
@Composable
// Here is where the cards actually are
fun DestinationRow(dest: HolidayDestination, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            // This makes teh image slowly fade out...
            // i had this idea from a website but doing it here was about 40 times harder

            // this also wasnt in any of the slides which is really fair
            // i shouldnt have done it but thought it would be really cool...
            AsyncImage(
                model = dest.imageUrl,
                contentDescription = dest.name,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(160.dp)
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    // keep left side fully visible
                                    Color.Black,
                                    // fade out to transparent on right
                                    Color.Transparent
                                ),
                                startX = 0f,
                                // cover full width
                                endX = size.width
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(42.dp))
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(dest.name, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}