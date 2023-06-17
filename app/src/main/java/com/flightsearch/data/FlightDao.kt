package com.flightsearch.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightDao {

    /**
    Airport
     **/
    @Query(" SELECT * FROM airport ")
    fun getAllAirports(): Flow<List<Airport>>

    @Query(
        """
            SELECT * FROM airport
            WHERE name LIKE '%' || :searchInput || '%' OR iata_code LIKE '%' || :searchInput || '%'
        """
    )
    fun getAirportsByInput(searchInput: String): Flow<List<Airport>>

    @Query(
        """
            SELECT iata_code FROM airport 
            WHERE iata_code 
            NOT IN (:codeToExclude)
        """
    )
    fun getAllCodesExcept(codeToExclude: String): Flow<List<String>>

    @Query(
        """
    Select * from airport
    WHERE iata_code = :code
        """
    )
    fun getAirportByCodeFlow(code: String): Flow<Airport>


    /**
    Favorite
     **/

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("""
        SELECT * FROM favorite
        WHERE departure_code = :departureCode
        AND destination_code = :destinationCode
    """)
    fun getSingleFavorite(departureCode: String, destinationCode: String):Flow<Favorite>

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFlight(flight: Favorite)
    @Delete
    suspend fun deleteFavoriteFlight(flight: Favorite)
}