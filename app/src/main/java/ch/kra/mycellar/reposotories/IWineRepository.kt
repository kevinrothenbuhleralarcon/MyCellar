package ch.kra.mycellar.reposotories

import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.util.Ressource
import kotlinx.coroutines.flow.Flow

interface IWineRepository {
    fun getAllWine(): Flow<List<Wine>>
    fun getWineByType(wineType: Int): Flow<List<Wine>>

    suspend fun getWine(wineId: Int): Ressource<Wine>

    suspend fun insert(wine: Wine)

    suspend fun delete(wine: Wine)

    suspend fun update(wine: Wine)
}