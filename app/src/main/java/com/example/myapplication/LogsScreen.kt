package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert

// Screen 3
// Already created logs screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(navController: NavHostController, viewModel: HolidayViewModel) {
    // This is to track which log the user would want to edit and/or delete
    var logToDelete by remember { mutableStateOf<LogEntry?>(null)}
    var logToEdit by remember { mutableStateOf<LogEntry?>(null)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Logs") },
                navigationIcon = {
                    // Back button
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                // If no logs exist let the user know
                Text("No logs yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                // Here are each log in a card
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
                            // this could maybe look nicer with an image or something...
                            Text("Note: ${log.note}")
                            Text("Duration: ${log.duration}")
                            Text("Category: ${log.category}")
                        }
                        // here is the 3 dots for extra options
                        var expanded by remember { mutableStateOf(false)}
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Options")
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                // Here is the edit button
                                text = { Text("Edit") },
                                onClick = {
                                    // make the current log the one that is going to be edited
                                    expanded = false
                                    logToEdit = log
                                }
                            )
                            DropdownMenuItem(
                                // here is the delete
                                text = { Text("Delete") },
                                onClick = {
                                    // Same thing as edit just delete instead
                                    expanded = false
                                    logToDelete = log
                                }
                            )

                        }
                    }
                }
            }
        }
        // TO EDIT!!
        if (logToEdit != null) {
            AlertDialog(
                // Make an alert I think this looks best ( i dont want to make a new screen )
                onDismissRequest = { logToEdit = null },
                title = { Text("Edit Log")},
                text = {
                    Column {
                        // each thing can be edited.
                        var note by remember { mutableStateOf(logToEdit!!.note)}
                        var duration by remember { mutableStateOf(logToEdit!!.duration)}
                        var category by remember {mutableStateOf(logToEdit!!.category)}

                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("Note") }
                        )
                        OutlinedTextField(
                            value = duration,
                            onValueChange = { duration = it },
                            label = { Text("Duration") }
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Category") }
                        )
                        // if confirmed then the i use the "updateLog" function i made earlier
                        Button(
                            onClick = {
                                viewModel.updateLog(logToEdit!!, note, duration, category)
                                logToEdit = null
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Save")
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    // else it exists ands stops being the "to edit log"
                    TextButton(onClick = { logToEdit = null}) {
                        Text("Cancel")
                    }
                }
            )
        }
        // TO DELETE!!
        if (logToDelete != null) {
            AlertDialog(
                // here is the alert asking if the user is sure that they want to delete
                onDismissRequest = { logToDelete = null },
                title = { Text("Delete note?") },
                text =  { Text("Are you sure you want to delete") },
                confirmButton = {
                    TextButton (
                        onClick = {
                            // If yes then i run the deleteLog function in my viewmodel
                            viewModel.deleteLog(logToDelete!!)
                            logToDelete = null
                        }
                    ) {
                        Text ("Confirm")
                    }
                },
                dismissButton = {
                    // If not then remove the marker that its the current "log to delete"
                    TextButton(onClick = { logToDelete = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
