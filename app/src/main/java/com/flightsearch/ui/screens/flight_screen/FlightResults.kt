package com.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flightsearch.data.MockData
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite

@Composable
fun FlightResults(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClick: (String, String) -> Unit
) {
    Column() {
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            items(destinationList, key = {it.id}){ item ->
                val isFavorite = favoriteList.find{f ->
                    f.departureCode == departureAirport.iataCode &&
                            f.destinationCode == item.iataCode
                }

                FlightRow(
                    isFavorite = isFavorite != null,
                    departureAirportCode = departureAirport.iataCode,
                    departureAirportName = departureAirport.name,
                    destinationAirportCode = item.iataCode,
                    destinationAirportName = item.name,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Preview
@Composable
fun FlightResultsPreview() {
    val mockData = MockData.airports

    FlightResults(
        departureAirport = mockData[0],
        destinationList = mockData,
        favoriteList = emptyList(),
        onFavoriteClick = { _: String, _: String -> }
    )
}