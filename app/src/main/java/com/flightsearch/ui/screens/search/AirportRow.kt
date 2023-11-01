package com.flightsearch.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AirportRow(
    modifier: Modifier = Modifier,
    code: String,
    name: String,
    onSelectCode: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = {
                    if (code != "") {
                        onSelectCode(code)
                    }
                },
            )
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = code,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}