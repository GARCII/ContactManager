package com.lydia.contact.domain.usecase

import com.lydia.contact.domain.model.Contact
import com.lydia.contact.domain.repository.ContactRepository
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {

    suspend operator fun invoke(id: Int): Contact {
        return contactRepository.getContactById(id)
    }
}