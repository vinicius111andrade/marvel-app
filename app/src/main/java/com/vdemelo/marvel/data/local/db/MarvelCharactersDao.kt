package com.vdemelo.marvel.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Dao
interface MarvelCharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(doggoModel: List<MarvelCharacterEntity>)

    @Upsert
    suspend fun upsert(marvelCharacterEntity: MarvelCharacterEntity)

    @Query("SELECT * FROM marvelcharacterentity")
    fun selectAll(): List<MarvelCharacterEntity>

    @Query("DELETE FROM marvelcharacterentity")
    suspend fun clearAll()
}
