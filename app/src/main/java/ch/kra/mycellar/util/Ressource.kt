package ch.kra.mycellar.util

sealed class Ressource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T): Ressource<T>(data)
    class Error<T>(data: T? = null, message: String): Ressource<T>(data, message)
    class Loading<T>(data: T? = null): Ressource<T>(data)
}
