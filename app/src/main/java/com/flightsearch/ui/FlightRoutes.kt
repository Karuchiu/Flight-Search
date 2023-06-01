package com.flightsearch.ui

import android.graphics.Insets.add
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.data.Favorite
import com.flightsearch.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FlightRoutes : NavigationDestination{
    override val route = "route"
    const val airportArgument = "airportDestinations"
    val routeWithArgs ="$route/{$airportArgument}"
}

@Composable
fun FlightRoutesScreen(
    modifier: Modifier = Modifier,
    destinationCodes: List<String>,
    departureCode: String,
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {

    val coroutineScope = rememberCoroutineScope()
    // Use a map to store the isFavorite state for each destination code
    val favoriteStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = destinationCodes
        ) { destinationCode ->
            // Get the isFavorite state for this destination code from the map,
            // or initialize it to false if it doesn't exist yet
            val isFavorite = favoriteStates.getOrPut(destinationCode) { false }
            Card(
                modifier = modifier.padding(vertical = 10.dp),
                elevation = 12.dp,
            ) {
                Row(
                    modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "DEPART",
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(300)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(start = 10.dp, end = 10.dp)
                        )
                        Text(
                            text = departureCode,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(500)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(start = 10.dp, end = 10.dp)
                        )
                        Text(
                            text = "ARRIVE",
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(300)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(start = 10.dp, end = 10.dp)
                        )
                        Text(
                            text = destinationCode,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(500)
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(start = 10.dp, end = 10.dp)
                        )
                    }

                    Spacer(modifier = modifier.weight(1f))

                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = "Add to Favorite Flights",
                        modifier = modifier
                            .padding(16.dp)
                            .clickable(
                                onClick = {
                                    val newFavoriteState = !isFavorite
                                    // Update the isFavorite state for this destination code only
                                    favoriteStates[destinationCode] = newFavoriteState

                                    if (newFavoriteState) {
                                        // Save departureCode and destinationCode to the database
                                        val favoriteFlight = Favorite(
                                            departureCode = departureCode,
                                            destinationCode = destinationCode
                                        )

                                        viewModel.addFavoriteFlight(favoriteFlight)


                                    } else {
                                        // Remove departureCode and destinationCode from the database
                                        val favoriteFlight = Favorite(
                                            departureCode = departureCode,
                                            destinationCode = destinationCode
                                        )


                                        viewModel.removeFavoriteFlight(favoriteFlight)


                                        // Remove the isFavorite state for this destination code from the map
                                        favoriteStates.remove(destinationCode)
                                    }
                                }
                            ),
                        tint = if (isFavorite) Color.Yellow else Color.Unspecified
                    )
                }
            }
        }
    }
}