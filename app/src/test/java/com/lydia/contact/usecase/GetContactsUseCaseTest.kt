package com.lydia.contact.usecase


import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.mapper.toContact
import com.lydia.contact.domain.repository.ContactRepository
import com.lydia.contact.domain.usecase.GetContactsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetContactsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var repository: ContactRepository

    @InjectMockKs
    lateinit var getContactsUseCase: GetContactsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun test_get_contacts() = runTest(testDispatcher) {
        val expectedData =
            ContactEntity(
                id = 1,
                email = "test@test",
                firstname = "Julien",
                lastname = "song",
                dob = "30-07-1994",
                gender = "male",
                nat = "FR",
                country = "France",
                state = "Paris",
                street = "Opéra",
                postCode = "75000",
                latitude = "-25.45",
                longitude = "-37.743",
                phone = "0666263798",
                mediumImageUrl = "urlmedium",
                largeImageUrl = "url",
            )

        coEvery { repository.getContacts() } returns flowOf(PagingData.from(listOf(expectedData)))

        val result = getContactsUseCase().first().collect()

        assertEquals(result, listOf(expectedData.toContact()))
        coVerify { repository.getContacts() }
    }


    private suspend fun <T : Any> PagingData<T>.collect(): List<T> {
        val dcb = object : DifferCallback {
            override fun onChanged(position: Int, count: Int) {}
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
        }
        val items = mutableListOf<T>()
        val dif = object : PagingDataDiffer<T>(dcb, testDispatcher) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? {
                for (idx in 0 until newList.size)
                    items.add(newList.getFromStorage(idx))
                onListPresentable()
                return null
            }
        }
        dif.collectFrom(this)
        return items
    }
}