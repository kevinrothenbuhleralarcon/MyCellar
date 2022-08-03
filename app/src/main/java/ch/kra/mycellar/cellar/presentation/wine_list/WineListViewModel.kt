package ch.kra.mycellar.cellar.presentation.wine_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.domain.repository.WineRepository
import ch.kra.mycellar.core.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class WineListViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val wineType = MutableStateFlow(WineType.ALL)
    private val listWine = wineType.flatMapLatest { getList(it) }

    private val _wineListState = mutableStateOf(WineListState())
    val wineListState: State<WineListState> = _wineListState

    init {
        wineType.combine(listWine) { wineType, listWine ->
            _wineListState.value = wineListState.value.copy(
                wineType = wineType,
                wineList = listWine
            )
        }.launchIn(viewModelScope)
    }

    fun changeWineType(newType: WineType) {
        wineType.value = newType
    }

    private fun getList(wineType: WineType): Flow<List<Wine>> {
        return if (wineType == WineType.ALL) {
            wineRepository.getAllWine().flowOn(dispatcher.io)
        } else {
            wineRepository.getWineByType(wineType).flowOn(dispatcher.io)
        }
    }

    fun changeQuantity(wine: Wine, add: Boolean) {
        if (!(wine.quantity == 0 && !add )) {
            var quantityChanged = -1
            if (add) { quantityChanged = 1 }
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