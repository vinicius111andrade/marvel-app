package com.vdemelo.marvel.data.local.db.characters

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class],
    version = 3
)
abstract class MarvelCharactersDataBase: RoomDatabase() {
    abstract val dao: MarvelCharactersDao
}
