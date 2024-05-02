package com.lydia.contact.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val firstname: String,
    val lastname: String,
    val dob: String,
    val gender: String,
    val nat: String,
    val country: String,
    val state: String,
    val street: String,
    val postCode: String,
    val latitude: String,
    val longitude: String,
    val phone: String,
    val mediumImageUrl: String,
    val largeImageUrl: String,
)
