package com.flightsearch.flight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flightsearch.search.AirportRow

@Composable
fun FlightRow(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    departureAirportCode: String,
    departureAirportName: String,
    destinationAirportCode: String,
    destinationAirportName: String,
    onFavoriteClick: (String, String) -> Unit
) {
    Card(
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Column(
                modifier = modifier.weight(10f)
            ){
                Text(
                    text = "Depart",
                    style = MaterialTheme.typography.overline,
                    modifier = modifier.padding(start = 32.dp)
                )
                AirportRow(code = departureAirportCode, name = departureAirportName)
                Text(
                    text = "Arrival",
                    style = MaterialTheme.typography.overline,
                    modifier = modifier.padding(start = 32.dp)
                )
                AirportRow(code = destinationAirportCode, name = destinationAirportName)
            }

            IconButton(
                onClick = {
                    onFavoriteClick(departureAirportCode, destinationAirportCode)
                },
                modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.LightGray
                )
            }
        }
    }
}