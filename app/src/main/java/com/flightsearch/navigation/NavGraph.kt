package com.flightsearch.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flightsearch.ui.screens.flight_screen.FlightScreen
import com.flightsearch.ui.screens.flight_screen.FlightScreenDestination
import com.flightsearch.ui.screens.search.SearchDestination
import com.flightsearch.ui.screens.search.SearchScreen

@Composable
fun FlightApp() {
    val navController = rememberNavController()

    Scaffold() { it ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = SearchDestination.route
        ){
            composable(route = SearchDestination.route){
                SearchScreen(
                    onSelectCode = {
                        navController.navigate("${FlightScreenDestination.route}/${it}")
                    }
                )
            }

            composable(
                route = FlightScreenDestination.routeWithArgs,
                arguments = listOf(navArgument(FlightScreenDestination.codeArg){
                    type = NavType.StringType
                })
            ){
                //val departureCode = it.arguments?.getString(FlightRoutes.airportArgument)
                    //?: error("airportArgument cannot be null")
                FlightScreen()
            }
        }
    }
}