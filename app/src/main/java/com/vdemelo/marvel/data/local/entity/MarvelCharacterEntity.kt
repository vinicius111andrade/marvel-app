package com.vdemelo.marvel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MarvelCharacterEntity(
    @PrimaryKey
    val charSum: Long,
    val id  : Int?,
    val name : String?,
    val description : String?,
    val thumbnailUrl : String?,
    var isFavorite: Boolean
)
