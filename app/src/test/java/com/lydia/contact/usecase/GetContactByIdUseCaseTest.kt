package com.lydia.contact.usecase

import com.lydia.contact.domain.model.Address
import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.model.Location
import com.lydia.contact.domain.repository.ContactRepository
import com.lydia.contact.domain.usecase.GetContactByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetContactByIdUseCaseTest {

    @MockK
    private lateinit var repository: ContactRepository

    @InjectMockKs
    lateinit var getContactByIdUseCase: GetContactByIdUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_get_contact_by_id() = runTest {
        val contactId = 1
        val contact = Contact(
            id = contactId,
            firstname = "Gabriela",
            lastname = "Sandbakk",
            dob = "1963-11-20T15:18:45.274Z",
            email = "swgnd.hmdy@example.com",
            gender = "female",
            nat = "FI",
            address = Address(
                "Finland",
                "Siikainen",
                "Newton Street",
                "37984",
                Location("27.4002", "63.1324")
            ),
            phone = "047-94733956",
            mediumImageUrl = "https://randomuser.me/api/portraits/med/women/42.jpg",
            largeImageUrl = "https://randomuser.me/api/portraits/med/women/42.jpg"
        )
        coEvery { repository.getContactById(contactId) } returns contact

        val result = getContactByIdUseCase(contactId)

        assertEquals(result, contact)
        coVerify { repository.getContactById(contactId) }
    }
}