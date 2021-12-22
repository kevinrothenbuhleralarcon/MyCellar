package ch.kra.mycellar.ui.viewmodels

import androidx.lifecycle.*
import ch.kra.mycellar.WineType
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.reposotories.WineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineListViewModel @Inject constructor(
    private val wineRepository: WineRepository
) : ViewModel() {

    private var _wineType = MutableLiveData(WineType.ALL.resId)
    val wineType: LiveData<Int> get() = _wineType
    //var listWine = mutableStateOf<List<Wine>>(listOf())
    val listWine: LiveData<List<Wine>> = Transformations.switchMap(_wineType) { _ -> getList() }


    fun changeWineType(wineType: Int) {
        _wineType.value = wineType
        getList()
    }

    private fun getList(): LiveData<List<Wine>> {
        return if (wineType.value == WineType.ALL.resId) {
            wineRepository.getAllWine().asLiveData()
        } else {
            wineRepository.getWineByType(wineType.value!!).asLiveData()
        }
    }

    /*private fun getList() {
        viewModelScope.launch {
            if (wineType.value == WineType.ALL.resId) {
                println("getAllList")
                wineRepository.getAllWine().collectLatest {
                    listWine.value = it
                }
            } else {
                println("getFilteredList")
                wineRepository.getWineByType(wineType.value!!).collectLatest {
                    listWine.value = it
                }
            }
        }

    }*/

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

    private fun getUpdateWineEntry(wineId: Int, wineName: String, wineType: Int, quantity: String, offeredBy: String): Wine {
        return Wine(
            id = wineId,
            wineName = wineName,
            wineType = wineType,
            quantity = quantity.toInt(),
            offeredBy = offeredBy
        )
    }

    private fun updateWine(wineId: Int, wineName: String, wineType: Int, quantity: String, offeredBy: String){
        updateWine(getUpdateWineEntry(wineId, wineName, wineType, quantity, offeredBy))
    }

    private fun updateWine(wine: Wine) {
        viewModelScope.launch {
            wineRepository.update(wine)
        }
    }
}