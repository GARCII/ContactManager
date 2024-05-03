package com.lydia.contact.navigation

sealed class Route(val route: String) {
    data object Splash : Route("splash")
    data object Contacts : Route("contacts")
    data object ContactDetail : Route("contactDetails")
}