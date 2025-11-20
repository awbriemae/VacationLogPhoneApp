package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HolidayViewModel : ViewModel() {
    // user note for the details
    var userNote = mutableStateOf("")
    // duration for how long they stayed
    var duration = mutableStateOf("")
    // category was for like "vacation, business, family"
    // it couldve been a drop down choose menu but i couldnt think of enough categories
    var category = mutableStateOf("")
    var logs = mutableStateListOf<LogEntry>()

    fun saveLog(name: String) {
        // function to save logs, simply adding them to the list then
        logs.add(LogEntry(name, userNote.value, duration.value, category.value))
    }

    // here is the functions to delete the log
    // very complex :/
    fun deleteLog(entry: LogEntry) {
        logs.remove(entry)
    }

    // Here is to update logs
    fun updateLog(oldEntry: LogEntry,
                  // take in the new entries
                  newNote: String,
                  newDuration: String,
                  newCategory: String) {
        // get the index in the list of the current log
        val index = logs.indexOf(oldEntry)
        if (index != -1) {
            logs[index] = oldEntry.copy(
                // change the values to the new one
                note = newNote,
                duration = newDuration,
                category = newCategory
            )
        }
    }
}

// How a logentry is stored
data class LogEntry(
    val destinationName: String,
    val note: String,
    val duration: String,
    val category: String
)