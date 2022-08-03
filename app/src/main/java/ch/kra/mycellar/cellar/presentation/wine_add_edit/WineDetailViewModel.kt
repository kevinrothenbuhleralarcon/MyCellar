package ch.kra.mycellar.cellar.presentation.wine_add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.repository.IWineRepository
import ch.kra.mycellar.core.DispatcherProvider
import ch.kra.mycellar.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineDetailViewModel @Inject constructor(
    private val wineRepository: IWineRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    suspend fun getWine(wineId: Int): Resource<Wine> {
        return wineRepository.getWine(wineId)
    }

    fun isEntryValid(wineName: String, quantity: String): Boolean {
        if (wineName.isBlank()  || quantity.isBlank() || quantity.toIntOrNull() == null) {
            return false
        }
        return true
    }

    fun updateWine(wineId: Int, wineName: String, wineType: Int, quantity: String, offeredBy: String){
        updateWine(getUpdateWineEntry(wineId, wineName, wineType, quantity, offeredBy))
    }

    fun addWine(wineName: String, wineType: Int, quantity: String, offeredBy: String) {
        insertWine(getNewWineEntry(wineName, wineType, quantity, offeredBy))
    }

    fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            wineRepository.delete(wine)
        }
    }

    private fun getNewWineEntry(wineName: String, wineType: Int, quantity: String, offeredBy: String): Wine {
        return Wine(
            wineName = wineName,
            wineType = wineType,
            offeredBy = offeredBy,
            quantity = quantity.toInt()
        )
    }

    private fun getUpdateWineEntry(wineId: Int, wineName: String, wineType: Int, quantity: String, offeredBy: String): Wine {
        return Wine(
            id = wineId,
            wineName = wineName,
            wineType = wineType,
            quantity = quantity.toInt(),
            offeredBy = offeredBy
        )
    }

    private fun updateWine(wine: Wine) {
        viewModelScope.launch(dispatchers.io) {
            wineRepository.update(wine)
        }
    }

    private fun insertWine(wine: Wine) {
        viewModelScope.launch(dispatchers.io){
            wineRepository.insert(wine)
        }
    }
}