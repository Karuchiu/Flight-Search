package com.flightsearch.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flightsearch.navigation.NavigationDestination

object FlightRoutes : NavigationDestination{
    override val route = "route"
}

@Composable
fun FlightRoutesScreen(
    modifier: Modifier = Modifier
) {
        Text(text = "Progress is important!!")
}