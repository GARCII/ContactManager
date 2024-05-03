package com.lydia.contact.ui.contactdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydia.contact.domain.usecase.GetContactByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getContactByIdUseCase: GetContactByIdUseCase
): ViewModel() {

    private val contactId = savedStateHandle.get<Int>("contactId") ?: 0
    val contact: MutableStateFlow<ContactDetailUi> = MutableStateFlow(ContactDetailUi())

    init {
        getContactDetail()
    }

    private fun getContactDetail() {
        viewModelScope.launch {
            val contactDetail = getContactByIdUseCase(contactId)
            val contactDetailUi = ContactDetailUi(
                name = "${contactDetail.firstname} ${contactDetail.lastname}",
                email = contactDetail.email,
                phone = contactDetail.phone,
                address = contactDetail.address.street,
                dob = contactDetail.dob,
                picture = contactDetail.largeImageUrl
            )
            contact.value = contactDetailUi
        }
    }
}