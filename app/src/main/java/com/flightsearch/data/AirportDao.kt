package com.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query(
        " SELECT * FROM airport "
    )
    fun getAll(): Flow<List<Airport>>
}