package com.lydia.contact.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lydia.contact.data.local.entity.ContactEntity

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<ContactEntity>)

    @Query("SELECT * FROM contact")
    fun pagingSource(): PagingSource<Int, ContactEntity>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Int): ContactEntity

    @Query("DELETE FROM contact")
    suspend fun clearAll()
}