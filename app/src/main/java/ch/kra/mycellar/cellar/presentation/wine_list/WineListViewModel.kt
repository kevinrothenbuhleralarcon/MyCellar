package ch.kra.mycellar.cellar.presentation.wine_list

import androidx.lifecycle.*
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.domain.repository.IWineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineListViewModel @Inject constructor(
    private val wineRepository: IWineRepository
) : ViewModel() {

    private var _wineType = MutableLiveData(WineType.ALL)
    val wineType: LiveData<WineType> get() = _wineType
    //var listWine = mutableStateOf<List<Wine>>(listOf())
    val listWine: LiveData<List<Wine>> = Transformations.switchMap(_wineType) { _ -> getList() }


    fun changeWineType(wineType: WineType) {
        _wineType.value = wineType
        getList()
    }

    private fun getList(): LiveData<List<Wine>> {
        return if (wineType.value == WineType.ALL) {
            wineRepository.getAllWine().flowOn(Dispatchers.IO).asLiveData()
        } else {
            wineRepository.getWineByType(wineType.value!!).flowOn(Dispatchers.IO).asLiveData()
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

    private fun getUpdateWineEntry(wineId: Int, wineName: String, wineType: WineType, quantity: String, offeredBy: String?): Wine {
        return Wine(
            id = wineId,
            wineName = wineName,
            wineType = wineType,
            quantity = quantity.toInt(),
            offeredBy = offeredBy
        )
    }

    private fun updateWine(wineId: Int, wineName: String, wineType: WineType, quantity: String, offeredBy: String?){
        updateWine(getUpdateWineEntry(wineId, wineName, wineType, quantity, offeredBy))
    }

    private fun updateWine(wine: Wine) {
        viewModelScope.launch {
            wineRepository.update(wine)
        }
    }
}