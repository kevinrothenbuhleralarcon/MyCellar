package ch.kra.mycellar.viewmodel

import androidx.lifecycle.*
import ch.kra.mycellar.WineType
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.database.WineDao
import kotlinx.coroutines.launch

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

    private var _wineType = MutableLiveData(WineType.ALL.strName)
    val wineType: LiveData<String> get() = _wineType
    val listWine: LiveData<List<Wine>> = Transformations.switchMap(_wineType) { _ -> getList() }

    fun getList(): LiveData<List<Wine>> {
        return if (wineType.value == WineType.ALL.strName) {
            wineDao.getAll().asLiveData()
        } else {
            wineDao.getByWineType(wineType.value!!).asLiveData()
        }
    }

    fun changeWineType(wineType: String) {
        _wineType.value = wineType
    }

    fun getWine(wineId: Int): LiveData<Wine> {
        return wineDao.getWine(wineId).asLiveData()
    }

    fun isEntryValid(wineName: String, wineType: String, quantity: String): Boolean {
        if (wineName.isBlank() || wineType.isBlank() || quantity.isBlank() || quantity.toIntOrNull() == null) {
            return false
        }
        return true
    }

    fun addWine(wineName: String, wineType: String, quantity: String, offeredBy: String) {
        insertWine(getNewWineEntry(wineName, wineType, quantity, offeredBy))
    }

    fun updateWine(wineId: Int, wineName: String, wineType: String, quantity: String, offeredBy: String){
        updateWine(getUpdateWineEntry(wineId, wineName, wineType, quantity, offeredBy))
    }

    fun deleteWine(wine: Wine) {
        viewModelScope.launch {
            wineDao.delete(wine)
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

    private fun getNewWineEntry(wineName: String, wineType: String, quantity: String, offeredBy: String): Wine {
        return Wine(
            wineName = wineName,
            wineType = wineType,
            offeredBy = offeredBy,
            quantity = quantity.toInt()
        )
    }

    private fun insertWine(wine: Wine) {
        viewModelScope.launch {
            wineDao.insert(wine)
        }
    }

    private fun getUpdateWineEntry(wineId: Int, wineName: String, wineType: String, quantity: String, offeredBy: String): Wine {
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
            wineDao.update(wine)
        }
    }
}