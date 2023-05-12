package com.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert
    suspend fun addFavoriteFlight(favoriteFlight: Favorite)

    @Delete
    suspend fun deleteFavoriteFlight(favoriteFlight: Favorite)
}