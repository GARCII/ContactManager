package com.lydia.contact

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.mapper.toContact
import com.lydia.contact.domain.model.Address
import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.model.Location
import com.lydia.contact.domain.repository.ContactRepository
import com.lydia.contact.domain.usecase.GetContactsUseCase
import com.lydia.contact.ui.contacts.ContactsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetContactsUseCaseTest {

    private lateinit var useCase: GetContactsUseCase
    private val contactRepository = mockk<ContactRepository>(relaxed = true)
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setUp() {
        dispatcher = StandardTestDispatcher()
        useCase = GetContactsUseCase(contactRepository = contactRepository)
        val viewModel = ContactsViewModel(useCase)
    }

    /*
    @Test
    fun test_contact_contain_one_to_ten() = runTest(dispatcher) {
        // Given
        val expectedContacts = (0..100).map {
            ContactEntity(
                id = it,
                email = "firstname",
                firstname = "firstname",
                lastname = "firstname",
                dob = "firstname",
                gender = "firstname",
                nat = "firstname",
                country = "firstname",
                state = "firstname",
                street = "firstname",
                postCode = "firstname",
                latitude = "firstname",
                longitude = "firstname",
                phone = "firstname",
                mediumImageUrl = "firstname",
                largeImageUrl = "firstname",
            )
        }

        coEvery { contactRepository.getContacts() } returns flowOf(PagingData.from(expectedContacts))

        val items: Flow<PagingData<Contact>> = useCase()
        val itemsSnapshot: List<Contact> = items.asSnapshot {
            scrollTo(index = 50)
        }

        val expected = (0..50).toList().size
        val actual = itemsSnapshot.size
        assert(expected == actual)
    }

     */
}