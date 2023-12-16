package com.flightsearch.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flightsearch.data.FlightRepository
import com.flightsearch.data.UserPreferencesRepository
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val airportList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    val flightRepository: FlightRepository,
    private val favoritePreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var deletedRecord: Favorite? = null

    private var getAirportsJob: Job? = null

    init {
        viewModelScope.launch {
            favoritePreferencesRepository.userPreferencesFlow.collect {
                processSearchQueryFlow(it.searchValue)
            }
        }
    }

    private fun processSearchQueryFlow(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isEmpty()) {
            viewModelScope.launch {
                refreshAirportList()
                refreshFavoriteList()
            }
        } else {
            getAirportsJob?.cancel()

            getAirportsJob = flightRepository.getAirportsByInput(query)
                //on each emission
                .onEach { result ->
                    _uiState.update {
                        uiState.value.copy(
                            airportList = result
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun refreshAirportList() {
        viewModelScope.launch {
            flightRepository.getAllAirports().collect { airports ->
                _uiState.value = _uiState.value.copy(airportList = airports)
            }
        }
    }

    private fun refreshFavoriteList() {
        viewModelScope.launch {
            flightRepository.getAllFavorites().collect { favorites ->
                _uiState.value = _uiState.value.copy(favoriteList = favorites)
            }
        }
    }

    fun updateQuery(searchQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }
        updatePreferenceSearchValue(searchQuery)
    }

    /**
     *  Essence of this code?
     *  [Why does this not have viewModelScope while others have]
     **/
    fun updateSelectedCode(selectedCode: String) {
        _uiState.update {
            it.copy(
                selectedCode = selectedCode
            )
        }
    }

    fun onQueryChange(query: String) {
        processSearchQueryFlow(query)
    }

    private fun updatePreferenceSearchValue(newValue: String) {
        viewModelScope.launch {
            favoritePreferencesRepository.updateUserPreferences(
                searchValue = newValue
            )
        }
    }

    fun removeFavorite(record: Favorite) {
        viewModelScope.launch {
            deletedRecord = record

            flightRepository.deleteFavoriteFlight(record)

            val newFavoriteList = uiState.value.favoriteList.filter {
                it != record
            }

            _uiState.update {
                uiState.value.copy(
                    favoriteList = newFavoriteList
                )
            }
        }
    }
}