package com.blez.anime_player_compose.core.local.listDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blez.anime_player_compose.common.util.Constants
import com.blez.anime_player_compose.core.entries.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(entity = ListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(data: ListEntity)

    @Delete(entity = ListEntity::class)
    suspend fun deleteAnimeList(data: ListEntity)

    @Query("SELECT * FROM ${Constants.LIST_ENTITY}")
    fun getLists(): Flow<List<ListEntity>>


}