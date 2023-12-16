package com.flightsearch.flight

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.data.MockData
import com.flightsearch.flight.viewmodel.FlightViewModel
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite

@Composable
fun FlightResults(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    viewModel: FlightViewModel = hiltViewModel()
) {
    val favoriteStatusMap by viewModel.favoriteStatusMapFlow.collectAsState(emptyMap())
    val context = LocalContext.current

    Column() {
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(destinationList, key = { it.id }) { item ->
                val isFavorite =
                    favoriteStatusMap[Pair(departureAirport.iataCode, item.iataCode)] ?: false

                FlightRow(
                    isFavorite = isFavorite,
                    departureAirportCode = departureAirport.iataCode,
                    departureAirportName = departureAirport.name,
                    destinationAirportCode = item.iataCode,
                    destinationAirportName = item.name,
                    onFavoriteClick = { s1, s2 ->
                        viewModel.addFavoriteFlight(s1, s2)
                        if (isFavorite){
                            Toast.makeText(context, "Removed Favorite", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Added Favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
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
        destinationList = mockData
    )
}