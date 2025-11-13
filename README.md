# Holiday Destinations App

## Extra Features & Creative UI Choices

    Image Fade Mask: Destination images use a horizontal gradient mask to fade smoothly into transparency.
    “View Logs” Button: A full‑width button at the top of the list screen provides quick access to saved logs. Its height and styling were customized to look non-invasive.
    Center‑Aligned Top Bar: The app bar title is centered using CenterAlignedTopAppBar to look balanced.
    Toast Notifications: Visual feedback is given when notes are updated, leaving the details screen and when logs are saved.
    Card Elevation & Rounded Corners: Cards use elevation and rounded corners to add depth and visual appeal.

## Lifecycle‑Aware Components & Effect Handlers

    LaunchedEffect: Used in DestinationDetailsScreen to trigger a toast whenever the note field changes, ensuring side effects run only when state updates.
    DisposableEffect: Cleans up when leaving the details screen by showing a toast, demonstrating lifecycle awareness of composables.
    rememberSaveable: Ensures text field states (note, duration, category) survive configuration changes like screen rotations.

## Search & Navigation

    Safe Argument Passing: Destination names and image URLs are URL‑encoded before navigation to avoid crashes with spaces or special characters.
    NavHost Setup: Three routes are defined — list, details/{name}/{imageUrl}, and logs — making navigation predictable and modular.
    Logs Screen: A dedicated screen displays previously saved entries with destination name, note, duration, and category.

## Floating Action Button (FAB)

    A FAB can be easily added to the HolidayListScreen or LogsScreen using Scaffold’s floatingActionButton slot. Example usage is here:
    Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("logs") }) {
            Icon(Icons.Default.List, contentDescription = "View Logs")
        }
    }
