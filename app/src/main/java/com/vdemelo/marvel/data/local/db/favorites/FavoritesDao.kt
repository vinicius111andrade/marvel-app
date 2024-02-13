package com.vdemelo.marvel.data.local.db.favorites

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vdemelo.marvel.data.local.entity.FavoriteEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favoriteentity")
    fun selectAll(): List<FavoriteEntity>

    @Upsert //TODO ver se faz o upsert corretamente, se n entra em conflito com a outra tabela
    suspend fun upsert(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favoriteentity WHERE charSum = :charSum")
    suspend fun deleteByCharSum(charSum: Long)
}
