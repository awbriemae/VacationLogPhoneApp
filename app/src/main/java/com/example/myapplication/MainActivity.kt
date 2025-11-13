package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHost
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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
fun HolidayListScreen(navController: NavHostController) {
    val destinations = listOf(
        HolidayDestination(
            "Warsaw",
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
                onClick = { navController.navigate("logs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // long but slim
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("View Logs")
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
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
            // Image with fade mask
            AsyncImage(
                model = dest.imageUrl,
                contentDescription = dest.name,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(160.dp)
                    .graphicsLayer { alpha = 0.99f } // force layer
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black,       // keep left side fully visible
                                    Color.Transparent  // fade out to transparent on right
                                ),
                                startX = 0f,
                                endX = size.width    // cover full width
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(42.dp))
            // Text content
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


// -------------------------------------------------------------------------------------------------
// Screen 2
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailsScreen(
    name: String,
    imageUrl: String?,
    navController: NavHostController,
    viewModel: HolidayViewModel
) {
    val context = LocalContext.current
    var note by rememberSaveable { mutableStateOf("") }
    var duration by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }

    // Show toast when note changes
    LaunchedEffect(note) {
        if (note.isNotEmpty()) {
            Toast.makeText(context, "Note updated!", Toast.LENGTH_SHORT).show()
        }
    }

    // Optional cleanup
    DisposableEffect(Unit) {
        onDispose {
            Toast.makeText(context, "Leaving details screen", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Talk about your visit to $name", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Note input field
            OutlinedTextField(
                value = note,
                onValueChange = { newValue ->
                    note = newValue
                    viewModel.userNote.value = newValue
                },
                label = { Text("Your Note") },
                modifier = Modifier.fillMaxWidth()
            )

            // Duration input field
            OutlinedTextField(
                value = duration,
                onValueChange = { newValue: String ->
                    duration = newValue
                    viewModel.duration.value = newValue
                },
                label = { Text("Duration") },
                modifier = Modifier.fillMaxWidth()
            )

            // Category input field
            OutlinedTextField(
                value = category,
                onValueChange = { newValue: String ->
                    category = newValue
                    viewModel.category.value = newValue
                },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.saveLog(name)
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                navController.navigate("logs")
            }) {
                Text("Save")
            }
        }
    }
}

// Screen 3
// Log Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(navController: NavHostController, viewModel: HolidayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Logs") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (viewModel.logs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No logs yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                items(viewModel.logs) { log ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Destination: ${log.destinationName}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Note: ${log.note}")
                            Text("Duration: ${log.duration}")
                            Text("Category: ${log.category}")
                        }
                    }
                }
            }
        }
    }
}

