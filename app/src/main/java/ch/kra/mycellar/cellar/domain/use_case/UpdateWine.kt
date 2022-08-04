package ch.kra.mycellar.cellar.domain.use_case

import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.repository.WineRepository

class UpdateWine(private val wineRepository: WineRepository) {
    suspend operator fun invoke(wine: Wine) {
        wineRepository.update(wine)
    }
}