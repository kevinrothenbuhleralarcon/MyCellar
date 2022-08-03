package ch.kra.mycellar.cellar.domain.repository

import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWineRepository {
    fun getAllWine(): Flow<List<Wine>>
    fun getWineByType(wineType: Int): Flow<List<Wine>>

    suspend fun getWine(wineId: Int): Resource<Wine>

    suspend fun insert(wine: Wine)

    suspend fun delete(wine: Wine)

    suspend fun update(wine: Wine)
}