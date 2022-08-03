package ch.kra.mycellar.cellar.data.local.converter

import androidx.room.TypeConverter
import ch.kra.mycellar.cellar.domain.WineType

class WineTypeConverter {
    @TypeConverter
    fun fromStringToWineType(value: String) = enumValueOf<WineType>(value)

    @TypeConverter
    fun toStringFromWineType(value: WineType) = value.name
}