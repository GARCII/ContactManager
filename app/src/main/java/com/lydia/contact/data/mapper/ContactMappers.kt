package com.lydia.contact.data.mapper

import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.remote.dto.ContactDto
import com.lydia.contact.domain.model.Address
import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.model.Location

fun ContactDto.toContactEntity(): ContactEntity = ContactEntity(
        id = 0,
        email = this.email,
        firstname = this.name.first,
        lastname = this.name.last,
        dob = this.dob.date,
        gender = gender,
        nat = this.nat,
        country = this.location.country,
        state = this.location.state,
        street = "${this.location.street.name} ${this.location.street.number}",
        postCode = this.location.postcode.toString(),
        latitude = this.location.coordinates.latitude,
        longitude = this.location.coordinates.longitude,
        phone = this.phone,
        mediumImageUrl = this.picture.medium,
        largeImageUrl = this.picture.large
    )

fun ContactEntity.toContact(): Contact {
    val location = Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
    val address = Address(
        country = this.country,
        state = this.state,
        street = this.street,
        postCode = this.postCode,
        location = location
    )
    return Contact(
        id = id,
        email = this.email,
        firstname = this.firstname,
        lastname = this.lastname,
        dob = this.dob,
        gender = gender,
        nat = this.nat,
        address = address,
        phone = phone,
        mediumImageUrl = mediumImageUrl,
        largeImageUrl = largeImageUrl
    )
}