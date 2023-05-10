package com.flightsearch.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flightsearch.R
import com.flightsearch.data.Airport
import com.flightsearch.data.Favorite
import com.flightsearch.navigation.NavigationDestination

object FlightSearchScreens: NavigationDestination{
    override val route = "flightSearch"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FlightSearchScreens(
    modifier: Modifier = Modifier,
    onAirportClick: () -> Unit = {},
    textValue: String,
    onValueChange: (String) -> Unit,
    airports: List<Airport>,
    favoriteFlights: List<Favorite>
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = textValue,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(R.string.search_airport),
                    style = MaterialTheme.typography.body1
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            modifier = modifier.fillMaxWidth()
        )
        if (textValue.isEmpty()){
            FavoriteFlightsScreen(favoriteFlights = favoriteFlights)
        }else{
            AirportDetailsScreen(
                airports = airports,
                onAirportClick = onAirportClick
            )
        }
    }

}

@Composable
fun AirportDetailsScreen(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    onAirportClick: () -> Unit = {}
) {
    AirportDetails(
        airports = airports,
        modifier = modifier,
        onAirportClick = onAirportClick
    )
}

@Composable
fun AirportDetails(
    modifier: Modifier = Modifier,
    airports: List<Airport>,
    onAirportClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ){
        items(
            items = airports,
            key = {it.id}
        ){ airport ->
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .clickable {
                        onAirportClick.invoke()
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = airport.iataCode,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = airport.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}

@Composable
fun FavoriteFlightsScreen(
    favoriteFlights: List<Favorite>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp)
    ){
        items(
            items = favoriteFlights,
            key = {it.id}
        ){ flight ->
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "DEPART",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = flight.departureCode,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = flight.destinationCode,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}