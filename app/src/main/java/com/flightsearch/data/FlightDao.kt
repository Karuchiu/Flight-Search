package com.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    /**
    Airport
     **/
    @Query(" SELECT * FROM airport ")
    fun getAllAirports(): List<Airport>

    @Query(
        """
            SELECT * FROM airport
            WHERE name LIKE '%' || :searchInput || '%' OR iata_code LIKE '%' || :searchInput || '%'
            ORDER BY name ASC
        """
    )
    fun getAirportsByInput(searchInput: String): Flow<List<Airport>>

    @Query(
        """
            SELECT * FROM airport 
            WHERE iata_code 
            NOT IN (:codeToExclude)
        """
    )
    fun getAllAirportsByCode(codeToExclude: String): Flow<List<Airport>>

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
    fun getSingleFavorite(departureCode: String, destinationCode: String):Favorite

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFlight(flight: Favorite)
    @Delete
    suspend fun deleteFavoriteFlight(flight: Favorite)
}