package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HolidayViewModel : ViewModel() {
    var userNote = mutableStateOf("")
    var duration = mutableStateOf("")
    var category = mutableStateOf("")
    var logs = mutableStateListOf<LogEntry>()

    fun saveLog(name: String) {
        logs.add(LogEntry(name, userNote.value, duration.value, category.value))
    }
}

data class LogEntry(
    val destinationName: String,
    val note: String,
    val duration: String,
    val category: String
)