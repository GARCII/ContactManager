package com.lydia.contact.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lydia.contact.data.local.ContactDatabase
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.mapper.toContact
import com.lydia.contact.data.remote.ContactRemoteMediator
import com.lydia.contact.data.remote.api.RandomUserApi
import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val database: ContactDatabase
): ContactRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getContacts(): Flow<PagingData<ContactEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = ContactRemoteMediator.Config.PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = ContactRemoteMediator(
                database = database,
                api = api),
            pagingSourceFactory = {
                database.dao().pagingSource()
            }
        ).flow
    }

    override suspend fun getContactById(id: Int): Contact =
        database.dao().getContactById(id).toContact()
}