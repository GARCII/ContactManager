package com.lydia.contact.data.remote.api

import com.lydia.contact.data.remote.dto.ContactsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface RandomUserApi {

    @GET("api/1.3/")
    suspend fun getContacts(
        @Query("seed") seed: String,
        @Query("results") results: Int,
        @Query("page") page: Int
    ): ContactsDto

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}