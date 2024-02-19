package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.dao.MarvelCharactersDao
import com.vdemelo.marvel.data.local.dao.RemoteKeysDao
import com.vdemelo.marvel.data.local.paging.RemoteKeys
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class, RemoteKeys::class],
    version = 8
)
abstract class MarvelCharactersDataBase: RoomDatabase() {
    abstract val itemsDao: MarvelCharactersDao
    abstract val keysDao: RemoteKeysDao
}
