package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

// Screen 2
// this is the details screen for each destination so when you clikc on it
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailsScreen(
    // variables i need for logs
    name: String,
    imageUrl: String?,
    navController: NavHostController,
    viewModel: HolidayViewModel
) {
    val context = LocalContext.current
    var note by rememberSaveable { mutableStateOf("") }
    var duration by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }

    // This lets the user know through a toast that their note is being updated
    // i shouldnt have it everytime it takes a change
    LaunchedEffect(note) {
        if (note.isNotEmpty()) {
            Toast.makeText(context, "Note updated!", Toast.LENGTH_SHORT).show()
        }
    }

    // Optional cleanup <- like mentioned in assessment
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
                        // bakc button
                        // this icon thing was really annoying to work with but really necessary
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            // Hey user this is what you want to do :D
            Text("Talk about your visit to $name", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Note input field
            OutlinedTextField(
                value = note,
                onValueChange = { newValue ->
                    // This is for the note variable in the log
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
                    // This is for the duration variable in the log
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
                    // This is for the categry variable in the log
                    category = newValue
                    viewModel.category.value = newValue
                },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // when clicked you can save the log using that function
                viewModel.saveLog(name)
                // let the user know they did nothing but got rewarded :)
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                navController.navigate("logs")
            }) {
                Text("Save")
            }
        }
    }
}
