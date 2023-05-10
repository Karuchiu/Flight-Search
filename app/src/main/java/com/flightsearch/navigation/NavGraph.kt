package com.flightsearch.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flightsearch.ui.FlightRoutes
import com.flightsearch.ui.FlightRoutesScreen
import com.flightsearch.ui.FlightSearchScreens

@Composable
fun FlightNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    
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
                    onAirportClick = {navController.navigate(FlightRoutes.route)}
                )
            }

            composable(route = FlightRoutes.route){
                FlightRoutesScreen()
            }
        }
    }
}