package ch.kra.mycellar

enum class WineType(val resId: Int) {
    RED_WINE(R.string.red_wine),
    WHITE_WINE(R.string.white_wine),
    ROSE_WINE(R.string.rose_wine),
    SPARKLING_WINE(R.string.sparkling_wine),
    DESSERT_WINE(R.string.dessert_wine),
    FORTIFIED_WINE(R.string.fortified_wine),
    ALL(R.string.all);
}