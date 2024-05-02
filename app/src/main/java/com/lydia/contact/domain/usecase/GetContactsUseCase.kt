package com.lydia.contact.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.lydia.contact.data.mapper.toContact
import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {
     operator fun invoke(): Flow<PagingData<Contact>> {
        return contactRepository.getContacts().map { it -> it.map { it.toContact() } }
    }
}