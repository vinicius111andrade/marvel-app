package com.vdemelo.marvel.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Dao
interface MarvelCharacterDao {

    @Upsert
    suspend fun upsertAll(characters: List<MarvelCharacterEntity>)

    //TODO need to check all queries afterwards and see if they actually do what I want
    @Query("SELECT * FROM marvelcharacterentity")
    fun pagingSource(): PagingSource<Int, MarvelCharacterEntity>

    //TODO need to check all queries afterwards and see if they actually do what I want
    @Query("DELETE FROM marvelcharacterentity")
    suspend fun clearAll()

}
