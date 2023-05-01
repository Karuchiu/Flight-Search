package com.flightsearch.ui

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flightsearch.R

@Composable
fun FlightSearchApp(
    viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
) {
    val focusManager = LocalFocusManager.current

    val fullSchedule by viewModel.getFullSchedule().collectAsState(emptyList())

    var airportSearch by remember{
        mutableStateOf("")
    }

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
        )
    )
}