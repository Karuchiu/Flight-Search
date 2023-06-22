package com.flightsearch.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flightsearch.R
import com.flightsearch.ui.FlightRoutes
import com.flightsearch.ui.FlightRoutesScreen
import com.flightsearch.ui.FlightSearchScreens
import com.flightsearch.ui.FlightSearchViewModel
import com.flightsearch.ui.screens.search.SearchDestination
import com.flightsearch.ui.screens.search.SearchScreen

@Composable
fun FlightApp() {
    val navController = rememberNavController()

    Scaffold() {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = SearchDestination.route
        ){
            composable(route = SearchDestination.route){
                SearchScreen(
                    onSelectCode = {
                        navController.navigate("")
                    },
                    onAirportClick = { it ->
                        navController.navigate("${FlightRoutes.route}/${it}")
                    },
                    textValue = airportSearch,
                    onValueChange = { it -> airportSearch = it},
                    airports = airportsList,
                    favoriteFlights = favoriteFlights
                )

            }

            composable(
                route = FlightRoutes.routeWithArgs,
                arguments = listOf(navArgument(FlightRoutes.airportArgument){
                    type = NavType.StringType
                })
            ){ it ->
                val departureCode = it.arguments?.getString(FlightRoutes.airportArgument)
                    ?: error("airportArgument cannot be null")
                val destinationCodes by viewModel.routeDestinations(departureCode).collectAsState(emptyList())
                FlightRoutesScreen(
                    departureCode = departureCode,
                    destinationCodes = destinationCodes,
                    favoriteState = viewModel.favoriteState.collectAsState().value,
                    setFavorite = viewModel::isFavorite,
                    flightSearchViewModel = viewModel
                )
            }
        }
    }
}