package ch.kra.mycellar.cellar.presentation.wine_list

import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType

data class WineListState(
    val isWineTypeExpanded: Boolean = false,
    val wineType: WineType = WineType.ALL,
    val wineList: List<Wine> = emptyList()
)
