package com.lydia.contact.navigation

sealed class Route(val route: String) {
    data object Contacts : Route("contacts")
    data object ContactDetail : Route("contactDetails")
}