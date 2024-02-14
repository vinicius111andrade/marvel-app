package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class],
    version = 4
)
abstract class MarvelCharactersDataBase: RoomDatabase() {
    abstract val dao: MarvelCharactersDao
}
