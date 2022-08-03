package ch.kra.mycellar.cellar.domain

enum class WineType(val resId: Int) {
    RED_WINE(1),
    WHITE_WINE(2),
    ROSE_WINE(3),
    SPARKLING_WINE(4),
    DESSERT_WINE(5),
    FORTIFIED_WINE(6),
    ALL(7);
}