package com.vdemelo.marvel.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity

@Dao
interface MarvelCharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marvelCharacterEntity: List<MarvelCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marvelCharacterEntity: MarvelCharacterEntity)

    @Query("DELETE FROM marvelcharacterentity")
    suspend fun clearAll()

    @Query("DELETE FROM marvelcharacterentity WHERE isFavorite=0")
    suspend fun clearAllExceptFavorites() //TODO ta certo?

    @Query("SELECT * FROM marvelcharacterentity ORDER BY name ASC")
    fun pageAll(): PagingSource<Int, MarvelCharacterEntity>

    @Query(
        "SELECT * FROM marvelcharacterentity WHERE " +
                "name LIKE :queryString ORDER BY name ASC"
    )
    fun pageByName(queryString: String): PagingSource<Int, MarvelCharacterEntity>

}
