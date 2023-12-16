package com.flightsearch.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import com.flightsearch.flight.FlightRow

@Composable
fun FavoriteResult(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClick: (String, String) -> Unit
    ) {
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            items(favoriteList, key = {it.id}){ item ->
                val departAirport = airportList.firstOrNull{
                    it.iataCode == item.departureCode
                }
                val destinationAirport = airportList.firstOrNull{
                    it.iataCode == item.destinationCode
                }

                if (departAirport != null && destinationAirport != null) {
                    FlightRow(
                        isFavorite = true,
                        departureAirportCode = departAirport.iataCode,
                        departureAirportName = departAirport.name,
                        destinationAirportCode = destinationAirport.iataCode,
                        destinationAirportName = destinationAirport.name,
                        onFavoriteClick = onFavoriteClick
                    )
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }
}