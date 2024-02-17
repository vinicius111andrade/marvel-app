package com.vdemelo.marvel.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Dao
interface MarvelFavoritesDao {

    @Upsert
    suspend fun upsert(marvelCharacterEntity: MarvelCharacterEntity)

    @Query("SELECT * FROM marvelcharacterentity")
    fun selectAll(): List<MarvelCharacterEntity>

    @Query("DELETE FROM marvelcharacterentity WHERE charSum = :charSum")
    suspend fun deleteByCharSum(charSum: Long)

}
