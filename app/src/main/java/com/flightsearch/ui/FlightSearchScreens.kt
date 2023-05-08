package com.flightsearch.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.R
import com.flightsearch.data.Airport

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {
    val focusManager = LocalFocusManager.current

    val fullScheduleTitle = stringResource(id = R.string.full_schedule)
    val fullSchedule by viewModel.getFullSchedule().collectAsState(emptyList())

    var airportSearch by remember{
        mutableStateOf("")
    }

    Scaffold(
        topBar = {

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = airportSearch,
                onValueChange = {airportSearch = it },
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
            FlightScheduleScreen(flightSchedules = fullSchedule)
        }
        


    }


}

@Composable
fun FlightScheduleScreen(
    flightSchedules: List<Airport>,
    modifier: Modifier = Modifier
) {
    FlightScheduleDetails(
        flightSchedules = flightSchedules,
        modifier = modifier
    )
}

@Composable
fun FlightScheduleDetails(
    flightSchedules: List<Airport>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ){
        items(
            items = flightSchedules,
            key = {it.id}
        ){ schedule ->
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = schedule.iataCode,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(300)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = schedule.name,
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