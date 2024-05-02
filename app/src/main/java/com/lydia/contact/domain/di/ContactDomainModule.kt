package com.lydia.contact.domain.di

import com.lydia.contact.domain.repository.ContactRepository
import com.lydia.contact.domain.usecase.GetContactByIdUseCase
import com.lydia.contact.domain.usecase.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactDomainModule {

    @Provides
    @Singleton
    fun provideGetContactsUseCase(
        contactRepository: ContactRepository
    ): GetContactsUseCase =
        GetContactsUseCase(
            contactRepository = contactRepository
        )

    @Provides
    @Singleton
    fun provideGetContactByIdUseCase(
        contactRepository: ContactRepository
    ): GetContactByIdUseCase =
        GetContactByIdUseCase(
            contactRepository = contactRepository
        )
}


