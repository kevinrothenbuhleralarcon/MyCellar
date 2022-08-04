package ch.kra.mycellar.core

sealed class UIEvent {
    data class Navigate(val route: String): UIEvent()
    object PopBackStack: UIEvent()
}
