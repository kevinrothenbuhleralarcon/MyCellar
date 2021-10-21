package ch.kra.mycellar.other

import android.content.Context
import ch.kra.mycellar.R
import ch.kra.mycellar.WineType

object CellarUtility {

    fun getWineTypeFromString(context: Context, wineType: String): WineType {
        return when(wineType) {
            context.getString(R.string.red_wine) -> WineType.RED_WINE
            context.getString(R.string.white_wine) -> WineType.WHITE_WINE
            context.getString(R.string.rose_wine) -> WineType.ROSE_WINE
            context.getString(R.string.sparkling_wine) -> WineType.SPARKLING_WINE
            context.getString(R.string.dessert_wine) -> WineType.DESSERT_WINE
            context.getString(R.string.fortified_wine) -> WineType.FORTIFIED_WINE
            else -> WineType.ALL
        }
    }
}