package com.lydia.contact.mediator

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lydia.contact.R
import com.lydia.contact.data.local.ContactDatabase
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.remote.ContactRemoteMediator
import com.lydia.contact.data.remote.api.RandomUserApi
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
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.slot
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class ContactRepositoryImplTest {

    @MockK
    private lateinit var database: ContactDatabase

    @MockK
    private lateinit var api: RandomUserApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )
        val transactionLambda = slot<suspend () -> R>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val mockContacts = List(20) { fakeContactList() }
        coEvery { api.getContacts(any(), any(), any()).results } returns mockContacts

        coEvery { database.dao().insertAll(any()) } just Runs
        coEvery {database.dao().clearAll() } just Runs

        val remoteMediator = ContactRemoteMediator(
            database,
            api
        )

        val pagingState = PagingState<Int, ContactEntity>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )

        val result =  remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        coEvery { api.getContacts(any(), any(), any()).results } returns emptyList()
        coEvery { database.dao().insertAll(any()) } just Runs
        coEvery {database.dao().clearAll() } just Runs

        val remoteMediator = ContactRemoteMediator(
            database,
            api
        )

        val pagingState = PagingState<Int, ContactEntity>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )

        val result =  remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenHttpErrorOccurs() = runTest {
        val message = "HttpException error"

        coEvery { api.getContacts(any(), any(), any()).results }  throws HttpException(
            message, Throwable()
        )
        coEvery { database.dao().insertAll(any()) } just Runs
        coEvery {database.dao().clearAll() } just Runs

        val remoteMediator = ContactRemoteMediator(
            database,
            api
        )

        val pagingState = PagingState<Int, ContactEntity>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is HttpException)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenIOErrorOccurs() = runTest {
        val message = "IOException Error"

        coEvery { api.getContacts(any(), any(), any()).results }  throws IOException(
            message, Throwable()
        )
        coEvery { database.dao().insertAll(any()) } just Runs
        coEvery {database.dao().clearAll() } just Runs

        val remoteMediator = ContactRemoteMediator(
            database,
            api
        )

        val pagingState = PagingState<Int, ContactEntity>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is IOException)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenAnyExceptionErrorOccurs() = runTest {
        val message = "Any Exception Error"

        coEvery { api.getContacts(any(), any(), any()).results }  throws Exception(
            message, Throwable()
        )
        coEvery { database.dao().insertAll(any()) } just Runs
        coEvery {database.dao().clearAll() } just Runs

        val remoteMediator = ContactRemoteMediator(
            database,
            api
        )

        val pagingState = PagingState<Int, ContactEntity>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is Exception)
    }

    private fun fakeContactList() = ContactDto(
        cell = "0941-937-0555",
        dob = DobDto(61, "1963-11-20T15:18:45.274Z"),
        email = "swgnd.hmdy@example.com",
        gender = "female",
        id = IdDto("TFN", "17419587"),
        location = LocationDto(
            "ساری",
            CoordinatesDto("27.4002", "63.1324"),
            "Iran",
            "96375",
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
}