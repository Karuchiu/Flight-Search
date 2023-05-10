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
import com.flightsearch.data.Favorite
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

    /*var isFavorite by remember{
        mutableStateOf(false)
    }*/

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
                    /*isFavorite = isFavorite,
                    onFavoriteChange = {
                        isFavorite = !isFavorite
                    }*/
                )
            }
        }
    }
}