package ch.kra.mycellar.cellar.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ch.kra.mycellar.R

enum class WineType(@StringRes val resId: Int, @DrawableRes val drawableId: Int) {
    RED_WINE(R.string.red_wine, R.drawable.red_wine),
    WHITE_WINE(R.string.white_wine, R.drawable.white_wine),
    ROSE_WINE(R.string.rose_wine, R.drawable.rose_wine),
    SPARKLING_WINE(R.string.sparkling_wine, R.drawable.sparkling_wine),
    DESSERT_WINE(R.string.dessert_wine, R.drawable.dessert_wine),
    FORTIFIED_WINE(R.string.fortified_wine, R.drawable.fortified_wine),
    ALL(R.string.all, 0);

    /*
    RED_WINE(1),
    WHITE_WINE(2),
    ROSE_WINE(3),
    SPARKLING_WINE(4),
    DESSERT_WINE(5),
    FORTIFIED_WINE(6),
    ALL(7);*/
}