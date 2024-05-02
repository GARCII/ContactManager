package com.lydia.contact.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactsDto(
    @Json(name = "results") val results: List<ContactDto>,
    @Json(name = "info") val info: InfoDto
)

@JsonClass(generateAdapter = true)
data class ContactDto(
    @Json(name = "cell") val cell: String,
    @Json(name = "dob") val dob: DobDto,
    @Json(name = "email") val email: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "id") val id: IdDto,
    @Json(name = "location") val location: LocationDto,
    @Json(name = "login") val login: LoginDto,
    @Json(name = "name") val name: NameDto,
    @Json(name = "nat") val nat: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "picture") val picture: PictureDto,
    @Json(name = "registered") val registered: Registered
)

@JsonClass(generateAdapter = true)
data class IdDto(
    @Json(name = "name") val name: String,
    @Json(name = "value") val value: String?
)

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "city") val city: String,
    @Json(name = "coordinates") val coordinates: CoordinatesDto,
    @Json(name = "country") val country: String,
    @Json(name = "postcode") val postcode: String,
    @Json(name = "state") val state: String,
    @Json(name = "street") val street: StreetDto,
    @Json(name = "timezone") val timezone: TimezoneDto
)

@JsonClass(generateAdapter = true)
data class DobDto(
    @Json(name = "age") val age: Int,
    @Json(name = "date") val date: String
)

@JsonClass(generateAdapter = true)
data class CoordinatesDto(
    @Json(name = "latitude") val latitude: String,
    @Json(name = "longitude") val longitude: String
)

@JsonClass(generateAdapter = true)
data class NameDto(
    @Json(name = "first") val first: String,
    @Json(name = "last") val last: String,
    @Json(name = "title") val title: String
)

@JsonClass(generateAdapter = true)
data class PictureDto(
    @Json(name = "large") val large: String,
    @Json(name = "medium") val medium: String,
    @Json(name = "thumbnail") val thumbnail: String
)

@JsonClass(generateAdapter = true)
data class Registered(
    @Json(name = "age") val age: Int,
    @Json(name = "date") val date: String
)

@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "md5") val md5: String,
    @Json(name = "password") val password: String,
    @Json(name = "salt") val salt: String,
    @Json(name = "sha1") val sha1: String,
    @Json(name = "sha256") val sha256: String,
    @Json(name = "username") val username: String,
    @Json(name = "uuid") val uuid: String
)

@JsonClass(generateAdapter = true)
data class StreetDto(
    @Json(name = "name") val name: String,
    @Json(name = "number") val number: Int
)

@JsonClass(generateAdapter = true)
data class TimezoneDto(
    @Json(name = "description") val description: String,
    @Json(name = "offset") val offset: String
)

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "seed") val seed: String,
    @Json(name = "results") val results: Int,
    @Json(name = "page") val page: Int,
    @Json(name = "version") val version: String,
)