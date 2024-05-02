package com.lydia.contact.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lydia.contact.data.local.entity.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun dao(): ContactDao
}