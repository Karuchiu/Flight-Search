package com.flightsearch.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flightsearch.R
import com.flightsearch.ui.FlightRoutes
import com.flightsearch.ui.FlightRoutesScreen
import com.flightsearch.ui.FlightSearchScreens
import com.flightsearch.ui.FlightSearchViewModel

@Composable
fun FlightNavHost(
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {
    val navController = rememberNavController()

    var airportSearch by remember{
        mutableStateOf("")
    }

    val fullScheduleTitle = stringResource(id = R.string.full_schedule)
    val airportsList by viewModel.getByUserInput(airportSearch).collectAsState(emptyList())
    val favoriteFlights by viewModel.getFavoriteFlights().collectAsState(emptyList())
    
    Scaffold(
        topBar = {}
    ) {
        NavHost(
            navController = navController,
            modifier = modifier.padding(it),
            startDestination = FlightSearchScreens.route
        ){
            composable(route = FlightSearchScreens.route){
                FlightSearchScreens(
                    onAirportClick = {navController.navigate(FlightRoutes.route)},
                    textValue = airportSearch,
                    onValueChange = { it -> airportSearch = it},
                    airports = airportsList,
                    favoriteFlights = favoriteFlights
                )

            }

            composable(route = FlightRoutes.route){
                FlightRoutesScreen()
            }
        }
    }
}