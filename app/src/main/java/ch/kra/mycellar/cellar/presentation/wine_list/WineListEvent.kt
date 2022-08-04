package ch.kra.mycellar.cellar.presentation.wine_list

import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType

sealed class WineListEvent {
    object WineTypeSelectionClicked: WineListEvent()
    data class WineTypeSelected(val wineType: WineType): WineListEvent()

    data class WineSelected(val wineId: Int): WineListEvent()
    data class ChangeQuantity(val wine: Wine, val isAdd: Boolean): WineListEvent()

    object AddNewWine: WineListEvent()
}
