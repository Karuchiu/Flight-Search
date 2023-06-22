package com.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite

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
                val departAirport = airportList.first{
                    it.iataCode == item.departureCode
                }
            }
        }
}