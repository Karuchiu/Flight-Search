package com.flightsearch.data

import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getAllAirports(): Flow<List<Airport>>

    fun getAirportsByInput(query: String): Flow<List<Airport>>

    fun getAllAirportsByCode(code: String): Flow<List<Airport>>

    fun getAirportByCodeFlow(code: String): Flow<Airport>

    suspend fun getAllFavorites(): Flow<List<Favorite>>

    fun getSingleFavorite(departureCode: String, destinationCode: String):Flow<Favorite?>

    suspend fun deleteFavoriteFlight(flight: Favorite)

    suspend fun insertFavoriteFlight(flight: Favorite)
}