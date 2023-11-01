package com.flightsearch.data

import com.flightsearch.models.Airport


object MockData {

    val airports = listOf(
        Airport(
            id = 1,
            iataCode = "OPO",
            name = "Francisco Sá Carne Airport",
            passengers = 5053134,
        ),
        Airport(
            id = 2,
            iataCode = "SAA",
            name = "Stockholm land Airport",
            passengers = 7494765,
        ),
        Airport(
            id = 3,
            iataCode = "WAW",
            name = "Warsaw Chopin Airport",
            passengers = 18860000,
        ),
        Airport(
            id = 4,
            iataCode = "MRS",
            name = "Marseille Provence Airport",
            passengers = 10151743,
        ),
        Airport(
            id = 5,
            iataCode = "BGY",
            name = "Milan Berg Airport",
            passengers = 3833063,
        ),
    )
}