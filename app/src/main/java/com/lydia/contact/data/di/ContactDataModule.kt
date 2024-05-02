package com.lydia.contact.data.di

import android.app.Application
import androidx.room.Room
import com.lydia.contact.data.local.ContactDatabase
import com.lydia.contact.data.remote.api.RandomUserApi
import com.lydia.contact.data.remote.api.RandomUserApi.Companion.BASE_URL
import com.lydia.contact.data.repository.ContactRepositoryImpl
import com.lydia.contact.domain.repository.ContactRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    fun provideContactsApiService(retrofit: Retrofit): RandomUserApi =
         retrofit.create(RandomUserApi::class.java)


    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase = Room.databaseBuilder(
        app,
        ContactDatabase::class.java,
        "contact_db"
    ).build()


    @Provides
    @Singleton
    fun provideContactRepository(
        api: RandomUserApi,
        database: ContactDatabase
    ): ContactRepository =
        ContactRepositoryImpl(
            api = api,
            database = database
        )
}


