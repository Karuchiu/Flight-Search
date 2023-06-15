package com.flightsearch.data

import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val flightDao: FlightDao): FlightRepository {
    override fun getAllAirports(): Flow<List<Airport>> {
        return flightDao.getAllAirports()
    }

    override fun getAirportsByInput(query: String): Flow<List<Airport>> {
        return flightDao.getAirportsByInput(query)
    }

    override fun getAllCodesExcept(code: String): Flow<List<String>> {
        return flightDao.getAllCodesExcept(code)
    }

    override fun getAirportByCodeFlow(code: String): Flow<Airport> {
        return flightDao.getAirportByCodeFlow(code)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return flightDao.getAllFavorites()
    }

    override fun getSingleFavorite(departureCode: String, destinationCode: String): Flow<Favorite> {
        return getSingleFavorite(departureCode,destinationCode)
    }

    override suspend fun deleteFavoriteFlight(flight: Favorite) {
        flightDao.deleteFavoriteFlight(flight)
    }

    override suspend fun insertFavoriteFlight(flight: Favorite) {
        flightDao.insertFavoriteFlight(flight)
    }
}