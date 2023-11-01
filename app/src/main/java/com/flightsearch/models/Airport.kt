package com.flightsearch.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Airport")
data class Airport(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo("iata_code")
    val iataCode: String = "",
    @ColumnInfo("name")
    val name: String = "",
    @ColumnInfo("passengers")
    val passengers: Int = 0
)