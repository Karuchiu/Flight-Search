package com.flightsearch.data

import com.flightsearch.db.FlightDao
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFlightRepository @Inject constructor(
    private val flightDao: FlightDao,
    val dispatcher: CoroutineDispatcher
): FlightRepository {
    override fun getAllAirports(): Flow<List<Airport>> {
        return flightDao.getAllAirports()
    }

    override fun getAirportsByInput(query: String): Flow<List<Airport>> {
        return flightDao.getAirportsByInput(query)
    }

    override fun getAllAirportsByCode(code: String): Flow<List<Airport>> {
        return flightDao.getAllAirportsByCode(code)
    }

    override fun getAirportByCodeFlow(code: String): Flow<Airport> {
        return flightDao.getAirportByCodeFlow(code)
    }

    override suspend fun getAllFavorites(): Flow<List<Favorite>> {
        return withContext(dispatcher) {
             flightDao.getAllFavorites()
        }
    }

    override fun getSingleFavorite(departureCode: String, destinationCode: String): Flow<Favorite?> {
        return flightDao.getSingleFavorite(departureCode,destinationCode)
    }

    override suspend fun deleteFavoriteFlight(flight: Favorite) {
        flightDao.deleteFavoriteFlight(flight)
    }

    override suspend fun insertFavoriteFlight(flight: Favorite) {
        flightDao.insertFavoriteFlight(flight)
    }
}