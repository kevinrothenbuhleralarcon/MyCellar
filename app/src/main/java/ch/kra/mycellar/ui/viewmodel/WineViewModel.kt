package ch.kra.mycellar.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.kra.mycellar.WineType
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.reposotories.WineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineViewModel @Inject constructor(
    private val wineRepository: WineRepository
) : ViewModel() {

    private var _wineType = mutableStateOf(WineType.ALL.resId)
    val wineType get() = _wineType
    var listWine = mutableStateOf<List<Wine>>(listOf())


    init {
        changeWineType(WineType.ALL.resId)
    }

    fun changeWineType(wineType: Int) {
        _wineType.value = wineType
        getList()
    }

    fun getWine(wineId: Int): Flow<Wine> {
        return wineRepository.getWine(wineId)
    }

    /*fun getWine(wineId: Int): LiveData<Wine> {
        return wineRepository.getWine(wineId).asLiveData()
    }*/

    fun isEntryValid(wineName: String, wineType: String, quantity: String): Boolean {
        if (wineName.isBlank() || wineType.isBlank() || quantity.isBlank() || quantity.toIntOrNull() == null) {
            return false
        }
        return true
    }

    fun addWine(wineName: String, wineType: Int, quantity: String, offeredBy: String) {
        insertWine(getNewWineEntry(wineName, wineType, quantity, offeredBy))
    }

    fun updateWine(wineId: Int, wineName: String, wineType: Int, quantity: String, offeredBy: String){
        updateWine(getUpdateWineEntry(wineId, wineName, wineType, quantity, offeredBy))
    }

    fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            wineRepository.delete(wine)
        }
    }

    fun changeQuantity(wine: Wine, add: Boolean) {
        if (!(wine.quantity == 0 && !add )) {
            var quantityChanged = -1
            if (add) {quantityChanged = 1}
            updateWine(
                wine.id,
                wine.wineName,
                wine.wineType,
                (wine.quantity + quantityChanged).toString(),
                wine.offeredBy
            )
        }
    }

    private fun getList() {
        viewModelScope.launch {
            if (wineType.value == WineType.ALL.resId) {
                wineRepository.getAllWine().collectLatest {
                listWine.value = it
                }
            } else {
                wineRepository.getWineByType(wineType.value!!).collectLatest {
                    listWine.value = it
                }
            }
        }

    }

    /*private fun getList(): LiveData<List<Wine>> {
        return if (wineType.value == WineType.ALL.resId) {
            wineRepository.getAllWine().asLiveData()
        } else {
            wineRepository.getWineByType(wineType.value!!).asLiveData()
        }
    }*/

    private fun getNewWineEntry(wineName: String, wineType: Int, quantity: String, offeredBy: String): Wine {
        return Wine(
            wineName = wineName,
            wineType = wineType,
            offeredBy = offeredBy,
            quantity = quantity.toInt()
        )
    }

    private fun insertWine(wine: Wine) {
        viewModelScope.launch {
            wineRepository.insert(wine)
        }
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
        viewModelScope.launch {
            wineRepository.update(wine)
        }
    }
}