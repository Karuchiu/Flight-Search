package com.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query(" SELECT * FROM airport ")
    fun getAll(): Flow<List<Airport>>

    @Query(
        """
            SELECT *
            FROM airport
            WHERE name LIKE '%' || :searchInput || '%' OR iata_code LIKE '%' || :searchInput || '%'
        """
    )
    fun getByUserInput(searchInput: String): Flow<List<Airport>>

    @Query(
        """
            SELECT iata_code FROM airport 
            WHERE iata_code 
            NOT IN (:codeToExclude)
        """
    )
    fun getAllCodesExcept(codeToExclude: String): Flow<List<String>>

}
