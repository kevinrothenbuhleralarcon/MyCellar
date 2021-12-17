package ch.kra.mycellar.reposotories

import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.database.WineDao
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WineRepository @Inject constructor(
    private val wineDao: WineDao
) {
    fun getAllWine() = wineDao.getAll()

    fun getWineByType(wineType: Int) = wineDao.getByWineType(wineType = wineType)

    fun getWine(wineId: Int) = wineDao.getWine(wineId = wineId)

    suspend fun insert(wine: Wine) = wineDao.insert(wine)

    suspend fun delete(wine: Wine) = wineDao.delete(wine)

    suspend fun update(wine: Wine) = wineDao.update(wine)
}