package ch.kra.mycellar.cellar.presentation.wine_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.domain.repository.WineRepository
import ch.kra.mycellar.cellar.domain.use_case.IncreaseDecreaseWineQuantity
import ch.kra.mycellar.core.DispatcherProvider
import ch.kra.mycellar.core.Route
import ch.kra.mycellar.core.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class WineListViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val increaseDecreaseWineQuantity: IncreaseDecreaseWineQuantity,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val wineType = MutableStateFlow(WineType.ALL)
    private val listWine = wineType.flatMapLatest { wineType ->
        if (wineType == WineType.ALL) {
            wineRepository.getAllWine().flowOn(dispatcher.io)
        } else {
            wineRepository.getWineByType(wineType).flowOn(dispatcher.io)
        }
    }

    private val _wineListState = mutableStateOf(WineListState())
    val wineListState: State<WineListState> = _wineListState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        wineType.combine(listWine) { wineType, listWine ->
            _wineListState.value = wineListState.value.copy(
                wineType = wineType,
                wineList = listWine
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: WineListEvent) {
        when (event) {
            is WineListEvent.WineTypeSelectionClicked -> {
                _wineListState.value = wineListState.value.copy(
                    isWineTypeExpanded = !wineListState.value.isWineTypeExpanded
                )
            }

            is WineListEvent.WineTypeSelected -> {
                wineType.value = event.wineType
            }

            is WineListEvent.WineSelected -> {
                sendUIEvent(UIEvent.Navigate(
                    route = "${Route.WINE_DETAIL_SCREEN}?${Route.Arguments.WINE_ID}=${event.wineId}"
                ))
            }

            is WineListEvent.ChangeQuantity -> {
                viewModelScope.launch(dispatcher.io) {
                    increaseDecreaseWineQuantity(event.wine, event.isAdd)
                }
            }

            is WineListEvent.AddNewWine -> {
                sendUIEvent(UIEvent.Navigate(
                    route = Route.WINE_DETAIL_SCREEN
                ))
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}