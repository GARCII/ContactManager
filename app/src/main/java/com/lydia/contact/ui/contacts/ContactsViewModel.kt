package com.lydia.contact.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lydia.contact.domain.usecase.GetContactsUseCase
import com.lydia.contact.network.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    getContactsUseCase: GetContactsUseCase,
    private val networkConnectivityObserver: ConnectivityObserver
): ViewModel() {

    val contactsFlow: Flow<PagingData<ContactUi>> =
        getContactsUseCase()
            .map {
                it.map { contact ->
                    ContactUi(
                        id = contact.id,
                        imageUrl = contact.mediumImageUrl,
                        name = "${contact.firstname} ${contact.lastname}",
                        phone = contact.phone,
                        email = contact.email
                    )
                }
            }.cachedIn(viewModelScope)

    val networkStatus = networkConnectivityObserver.observe().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ConnectivityObserver.Status.Unavailable
            )
}