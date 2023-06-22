package com.flightsearch.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AirportRow(
    modifier: Modifier,
    code: String,
    name: String,
    onSelectCode: (String) -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                if (code != ""){
                    onSelectCode(code)
                 }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = code)
        Text(text = name)
    }
}