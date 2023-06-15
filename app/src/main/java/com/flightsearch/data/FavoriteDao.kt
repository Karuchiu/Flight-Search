package com.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    fun getFavoriteFlight(departureCode: String, destinationCode: String):Flow<Favorite>

    @Insert
    suspend fun addFavoriteFlight(favoriteFlight: Favorite)

    @Delete
    suspend fun deleteFavoriteFlight(favoriteFlight: Favorite)
}