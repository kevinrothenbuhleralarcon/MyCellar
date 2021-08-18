package ch.kra.mycellar.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wine(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "wine_name") val wineName: String,
    @ColumnInfo(name = "offered_by") val offeredBy: String,
    @NonNull @ColumnInfo(name = "wine_type") val wineType: String,
    @ColumnInfo(name = "quantity") val quantity: Int
)
