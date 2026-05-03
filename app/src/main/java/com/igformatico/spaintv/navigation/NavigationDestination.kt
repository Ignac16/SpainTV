package com.igformatico.spaintv.navigation

sealed class NavigationDestination(val route: String, val label: String) {
    object Home : NavigationDestination("home", "HOME")
    object Channels : NavigationDestination("channels", "CHANNELS")
    object Guide : NavigationDestination("guide", "GUIDE")
    object Favourites : NavigationDestination("favourites", "FAVOURITES")
    object Settings : NavigationDestination("settings", "SETTINGS")
}
