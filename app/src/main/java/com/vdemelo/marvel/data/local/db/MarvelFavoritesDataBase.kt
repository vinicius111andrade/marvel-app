package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.dao.MarvelFavoritesDao
import com.vdemelo.marvel.data.local.paging.RemoteKeys
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class, RemoteKeys::class],
    version = 1
)
abstract class MarvelFavoritesDataBase: RoomDatabase() {
    abstract val favoritesDao: MarvelFavoritesDao
//    abstract val keysDao: RemoteKeysDao
// TODO acho q n vou precisar, quando for paginar mando pro paging DB e é sucesso
//  ou entao n preciso paginar, pq vai estar tudo offline, pego tudo e carrego
}
