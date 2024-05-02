package com.lydia.contact.domain.model

data class Contact(
    val id: Int,
    val email: String,
    val firstname: String,
    val lastname: String,
    val dob: String,
    val gender: String,
    val nat: String,
    val address: Address,
    val phone: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
)

data class Address(
    val country: String,
    val state: String,
    val street: String,
    val postCode: String,
    val location: Location,
)

data class Location(
    val latitude: String,
    val longitude: String,
)