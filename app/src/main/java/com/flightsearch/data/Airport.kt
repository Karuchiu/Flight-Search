package com.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int,
    @NotNull
    @ColumnInfo(name = "name")
    val name: String,
    @NotNull
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @NotNull
    @ColumnInfo(name = "passengers")
    val passengers: Int
)
