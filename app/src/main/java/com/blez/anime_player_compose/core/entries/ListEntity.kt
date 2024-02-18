package com.blez.anime_player_compose.core.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blez.anime_player_compose.common.util.Constants.LIST_ENTITY

@Entity(tableName = LIST_ENTITY)
data class ListEntity(
    @PrimaryKey val animeId: String,
    val image: String,
    val title: String,
    val episodeId : String,
    val episodeNo : String,
    )
