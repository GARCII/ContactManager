package com.lydia.contact.ui.di

import android.content.Context
import com.lydia.contact.network.ConnectivityObserver
import com.lydia.contact.network.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext appContext: Context
    ): ConnectivityObserver =
        NetworkConnectivityObserver(context = appContext)
}