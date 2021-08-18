package ch.kra.mycellar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.kra.mycellar.database.WineDao

class WineViewModel(private val wineDao: WineDao) : ViewModel() {

    class WineViewModelFactory(private val wineDao: WineDao): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WineViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WineViewModel(wineDao) as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }


}