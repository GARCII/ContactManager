package com.lydia.contact.domain.repository

import androidx.paging.PagingData
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getContacts(): Flow<PagingData<ContactEntity>>
    suspend fun getContactById(id: Int): Contact
}