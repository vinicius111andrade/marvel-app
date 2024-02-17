package com.vdemelo.marvel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RemoteKeys(
    @PrimaryKey
    val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
