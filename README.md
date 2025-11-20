# Holiday Destinations App

## Extra Features & Creative UI Choices

    Image Fade Mask: Destination images use a horizontal gradient mask to fade smoothly into transparency.
    “View Logs” Button: A button at the top of the list screen lets users quickly access saved logs. Its height and styling were customized to look non-invasive.
    Center‑Aligned Top Bar: The app bar title is centered using CenterAlignedTopAppBar to look nice.
    Toast Notifications: Visual feedback is given to users when notes are updated, leaving the details screen and when logs are saved.
    Card Elevation & Rounded Corners: Cards use elevation and rounded corners to look nicer and less sharp.
    Log Options Button: The logs now feature an extra options button that lets them edit and remove the logs. 

## Lifecycle‑Aware Components & Effect Handlers

    LaunchedEffect: Used in DestinationDetailsScreen to trigger a toast whenever the note field changes.
    DisposableEffect: Cleans up when leaving the details screen by showing a toast, showing lifecycle awareness of composables.
    rememberSaveable: Ensures text field states (note, duration, category) survive configuration changes like the screen being rotated.

## Search & Navigation

    Safe Argument Passing: Destination names and image URLs are URL‑encoded before navigation to avoid crashes with spaces or special characters.
    NavHost Setup: Three routes are defined — list, details/{name}/{imageUrl}, and logs — making navigation predictable and modular.
    Logs Screen: A dedicated screen displays previously saved entries with destination name, note, duration, category and options to edit or delete them.

## Floating Action Button (FAB)

    A FAB can be easily added to the HolidayListScreen or LogsScreen using Scaffold’s floatingActionButton slot. Example here:
    Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("logs") }) {
            Icon(Icons.Default.List, contentDescription = "View Logs")
        }
    }
