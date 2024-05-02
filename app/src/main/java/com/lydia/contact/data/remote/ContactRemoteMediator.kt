package com.lydia.contact.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lydia.contact.data.local.ContactDatabase
import com.lydia.contact.data.local.entity.ContactEntity
import com.lydia.contact.data.mapper.toContactEntity
import com.lydia.contact.data.remote.api.RandomUserApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ContactRemoteMediator(
    private val database: ContactDatabase,
    private val api: RandomUserApi
) : RemoteMediator<Int, ContactEntity>() {

    object Config {
        const val SEED = "lydia"
        const val PAGE_SIZE = 20
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ContactEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = false
                        )
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val contacts = api.getContacts(
                seed = Config.SEED,
                page = loadKey,
                results = state.config.pageSize
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.dao().clearAll()
                }
                val entities = contacts.results.map { it.toContactEntity() }
                database.dao().insertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = contacts.results.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}