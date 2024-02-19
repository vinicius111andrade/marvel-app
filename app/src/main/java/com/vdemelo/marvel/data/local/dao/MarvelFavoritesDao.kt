package com.vdemelo.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelFavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marvelCharacterEntity: MarvelCharacterEntity)

    @Query("SELECT * FROM marvelcharacterentity ORDER BY name ASC")
    fun selectAll(): List<MarvelCharacterEntity>

    @Query("SELECT * FROM marvelcharacterentity ORDER BY name ASC")
    fun allFavoritesFlow(): Flow<List<MarvelCharacterEntity>>

    @Query("DELETE FROM marvelcharacterentity WHERE charSum = :charSum")
    suspend fun deleteByCharSum(charSum: Long)
}
