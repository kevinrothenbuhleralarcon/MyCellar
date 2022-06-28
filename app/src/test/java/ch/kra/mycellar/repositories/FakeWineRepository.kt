package ch.kra.mycellar.repositories

import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.reposotories.IWineRepository
import ch.kra.mycellar.util.Ressource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class FakeWineRepository: IWineRepository {
    var currentWine: Wine? = null

    override fun getAllWine(): Flow<List<Wine>> {
        TODO("Not yet implemented")
    }

    override fun getWineByType(wineType: Int): Flow<List<Wine>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWine(wineId: Int): Ressource<Wine> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(wine: Wine) {
        delay(600)
        currentWine = wine
    }

    override suspend fun delete(wine: Wine) {
        TODO("Not yet implemented")
    }

    override suspend fun update(wine: Wine) {
        TODO("Not yet implemented")
    }
}