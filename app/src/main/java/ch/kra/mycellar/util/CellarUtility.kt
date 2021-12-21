package ch.kra.mycellar.util

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

    fun getStringFromWineType(context: Context, wineTypeId: Int): String {
        return when (wineTypeId) {
            WineType.RED_WINE.resId -> context.getString(R.string.red_wine)
            WineType.WHITE_WINE.resId -> context.getString(R.string.white_wine)
            WineType.ROSE_WINE.resId -> context.getString(R.string.rose_wine)
            WineType.SPARKLING_WINE.resId -> context.getString(R.string.sparkling_wine)
            WineType.DESSERT_WINE.resId -> context.getString(R.string.dessert_wine)
            WineType.FORTIFIED_WINE.resId -> context.getString(R.string.fortified_wine)
            else -> context.getString(R.string.all)
        }
    }

    fun getBackgroundIdFromType(context: Context, wineTypeId: Int): Int {
        return when (wineTypeId) {
            WineType.RED_WINE.resId -> R.drawable.red_wine
            WineType.WHITE_WINE.resId -> R.drawable.white_wine
            WineType.ROSE_WINE.resId -> R.drawable.rose_wine
            WineType.SPARKLING_WINE.resId -> R.drawable.sparkling_wine
            WineType.DESSERT_WINE.resId -> R.drawable.dessert_wine
            WineType.FORTIFIED_WINE.resId -> R.drawable.fortified_wine
            else -> 0
        }
    }
}