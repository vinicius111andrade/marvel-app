package com.vdemelo.marvel.data.local.db.favorites

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)
abstract class FavoritesDataBase: RoomDatabase() {
    abstract val dao: FavoritesDao
}
