package ch.kra.mycellar.cellar.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ch.kra.mycellar.cellar.domain.WineType

@Entity
data class Wine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "wine_name") val wineName: String,
    @ColumnInfo(name = "offered_by") val offeredBy: String?,
    @ColumnInfo(name = "wine_type") val wineType: WineType,
    val quantity: Int
)
