package com.vdemelo.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import kotlinx.coroutines.flow.Flow

//TODO ver quais estou usando de fato
@Dao
interface MarvelFavoritesDao {
    @Upsert
    suspend fun upsert(marvelCharacterEntity: MarvelCharacterEntity)

    @Query("SELECT * FROM marvelcharacterentity")
    fun selectAll(): List<MarvelCharacterEntity>

    @Query("SELECT * FROM marvelcharacterentity")
    fun allFavoritesFlow(): Flow<List<MarvelCharacterEntity>>

    @Query("DELETE FROM marvelcharacterentity WHERE charSum = :charSum")
    suspend fun deleteByCharSum(charSum: Long)
}
