package ch.kra.mycellar.cellar.domain.use_case

import ch.kra.mycellar.cellar.data.local.entity.Wine

class IncreaseDecreaseWineQuantity(private val updateWine: UpdateWine) {
    suspend operator fun invoke(wine: Wine, isIncrease: Boolean) {
        if (!(wine.quantity <= 0 && !isIncrease)) {
            var quantityModifier = -1
            if (isIncrease) quantityModifier = 1
            updateWine(
                wine.copy(
                    quantity = wine.quantity + quantityModifier
                )
            )
        }
    }
}