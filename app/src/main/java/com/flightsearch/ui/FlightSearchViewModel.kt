package com.flightsearch.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.flightsearch.FlightSearchApplication
import com.flightsearch.data.Airport
import com.flightsearch.data.AirportDao
import com.flightsearch.data.Favorite
import com.flightsearch.data.FavoriteDao
import com.flightsearch.data.FavoritePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val airPortDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val favoritePreferencesRepository: FavoritePreferencesRepository
): ViewModel() {

    //Read the favorite preference
    val favoriteState: StateFlow<FlightFavoriteState> =
        favoritePreferencesRepository.isFavorite.map { isFavorite ->
            FlightFavoriteState(isFavorite)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FlightFavoriteState()
            )
    fun getByUserInput(searchInput: String): Flow<List<Airport>> = airPortDao.getByUserInput(searchInput)

    fun getFavoriteFlights(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()

    fun addFavoriteFlight(favoriteFlight: Favorite) {
        viewModelScope.launch {
            //favoritePreferencesRepository.saveFavoritePreferences(true)
            favoriteDao.addFavoriteFlight(favoriteFlight)
        }
    }

    fun removeFavoriteFlight(favoriteFlight: Favorite) {
        viewModelScope.launch {
            //favoritePreferencesRepository.saveFavoritePreferences(false)
            favoriteDao.deleteFavoriteFlight(favoriteFlight)
        }
    }

    fun routeDestinations(departureCode: String): Flow<List<String>> = airPortDao
        .getAllCodesExcept(departureCode)

    fun getFavoriteFlight(departureCode: String, destinationCode: String): Flow<Favorite> = favoriteDao
        .getFavoriteFlight(departureCode, destinationCode)

    //Store the favorite preference
    fun isFavorite(isFavorite: Boolean){
        viewModelScope.launch {
            favoritePreferencesRepository.saveFavoritePreferences(isFavorite)
        }
    }

    companion object{
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    application.database.airportDao(),
                    application.database.favoriteDao(),
                    application.favoritePrefencesRepository
                )
            }
        }
    }
}

data class FlightFavoriteState(
    val isFavorite: Boolean = false
)

