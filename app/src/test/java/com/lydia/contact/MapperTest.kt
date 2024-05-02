package com.lydia.contact

import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.mapper.toContact
import com.lydia.contact.data.mapper.toContactEntity
import com.lydia.contact.data.remote.dto.ContactDto
import com.lydia.contact.data.remote.dto.CoordinatesDto
import com.lydia.contact.data.remote.dto.DobDto
import com.lydia.contact.data.remote.dto.IdDto
import com.lydia.contact.data.remote.dto.LocationDto
import com.lydia.contact.data.remote.dto.LoginDto
import com.lydia.contact.data.remote.dto.NameDto
import com.lydia.contact.data.remote.dto.PictureDto
import com.lydia.contact.data.remote.dto.Registered
import com.lydia.contact.data.remote.dto.StreetDto
import com.lydia.contact.data.remote.dto.TimezoneDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapperTest {

    private lateinit var contactDto: ContactDto
    private lateinit var contactEntity: ContactEntity

    @Before
    fun setup() {
        contactDto = ContactDto(
            cell = "0941-937-0555",
            dob = DobDto(61, "1963-11-20T15:18:45.274Z"),
            email = "swgnd.hmdy@example.com",
            gender = "female",
            id = IdDto("TFN", "174195871"),
            location = LocationDto(
                "ساری",
                CoordinatesDto("27.4002", "63.1324"),
                "Iran",
                96375,
                "آذربایجان شرقی",
                StreetDto("کارگر", 830),
                TimezoneDto("Brazil, Buenos Aires, Georgetown", "-3:00")
            ),
            login = LoginDto(
                "733fc3bc7ac7a1472dc756850fe2b1f2",
                "therock",
                "RoMBC7pm",
                "2dd6fe7b7383765dabeaf9dbea89e7ad55195041",
                "e10b496b10d76fd8c0b2074512fca7f14e3410c8cc75fc7f72658b2f5db58e7c",
                "purplebutterfly873",
                "80d51f70-fb39-477f-b3ea-1d5676be6c56"
            ),
            name = NameDto("Ms", "سوگند", "احمدی"),
            nat = "IR",
            phone = "047-94733956",
            picture = PictureDto(
                "https://randomuser.me/api/portraits/women/42.jpg",
                "https://randomuser.me/api/portraits/med/women/42.jpg",
                "https://randomuser.me/api/portraits/thumb/women/42.jpg"
            ),
            registered = Registered(15, "2009-02-24T13:53:58.299Z")
        )


        contactEntity = ContactEntity(
            id = 0,
            email = "swgnd.hmdy@example.com",
            firstname = "سوگند",
            lastname = "احمدی",
            dob = "1963-11-20T15:18:45.274Z",
            gender = "female",
            nat = "IR",
            country = "Iran",
            state = "آذربایجان شرقی",
            street = "کارگر 830",
            postCode = "96375",
            latitude = "27.4002",
            longitude = "63.1324",
            phone = "047-94733956",
            mediumImageUrl = "https://randomuser.me/api/portraits/med/women/42.jpg",
            largeImageUrl = "https://randomuser.me/api/portraits/women/42.jpg"
        )
    }


    @Test
    fun `test ContactDto to ContactEntity mapping`() {
        val result = contactDto.toContactEntity()

        assertEquals(0, result.id)
        assertEquals(contactDto.email, result.email)
        assertEquals(contactDto.name.first, result.firstname)
        assertEquals(contactDto.name.last, result.lastname)
        assertEquals(contactDto.dob.date, result.dob)
        assertEquals(contactDto.gender, result.gender)
        assertEquals(contactDto.nat, result.nat)
        assertEquals(contactDto.location.country, result.country)
        assertEquals(contactDto.location.state, result.state)
        assertEquals("${contactDto.location.street.name} ${contactDto.location.street.number}", result.street)
        assertEquals(contactDto.location.postcode.toString(), result.postCode)
        assertEquals(contactDto.location.coordinates.latitude, result.latitude)
        assertEquals(contactDto.location.coordinates.longitude, result.longitude)
        assertEquals(contactDto.phone, result.phone)
        assertEquals(contactDto.picture.medium, result.mediumImageUrl)
        assertEquals(contactDto.picture.large, result.largeImageUrl)
    }

    @Test
    fun `test ContactEntity to Contact mapping`() {
        val result = contactEntity.toContact()

        assertEquals(contactEntity.id, result.id)
        assertEquals(contactEntity.email, result.email)
        assertEquals(contactEntity.firstname, result.firstname)
        assertEquals(contactEntity.lastname, result.lastname)
        assertEquals(contactEntity.dob, result.dob)
        assertEquals(contactEntity.gender, result.gender)
        assertEquals(contactEntity.nat, result.nat)
        assertEquals(contactEntity.country, result.address.country)
        assertEquals(contactEntity.state, result.address.state)
        assertEquals(contactEntity.street, result.address.street)
        assertEquals(contactEntity.postCode, result.address.postCode)
        assertEquals(contactEntity.latitude, result.address.location.latitude)
        assertEquals(contactEntity.longitude, result.address.location.longitude)
        assertEquals(contactEntity.phone, result.phone)
        assertEquals(contactEntity.mediumImageUrl, result.mediumImageUrl)
        assertEquals(contactEntity.largeImageUrl, result.largeImageUrl)
    }
}