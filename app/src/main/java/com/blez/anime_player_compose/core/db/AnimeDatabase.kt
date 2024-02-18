package com.blez.anime_player_compose.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blez.anime_player_compose.core.entries.ListEntity
import com.blez.anime_player_compose.core.local.listDao.ListDao


@Database(entities = [ListEntity::class],version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {

    abstract val listDao : ListDao
}
