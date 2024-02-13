package com.vdemelo.marvel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoriteEntity(
    @PrimaryKey
    val charSum: Long
)
