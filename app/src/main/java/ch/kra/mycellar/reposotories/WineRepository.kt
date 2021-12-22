package ch.kra.mycellar.reposotories

import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.database.WineDao
import ch.kra.mycellar.util.Ressource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WineRepository @Inject constructor(
    private val wineDao: WineDao
) {
    fun getAllWine() = wineDao.getAll()

    fun getWineByType(wineType: Int) = wineDao.getByWineType(wineType = wineType)

    suspend fun getWine(wineId: Int): Ressource<Wine> {
        val response = try {
            wineDao.getWine(wineId = wineId)
        } catch (e: Exception) {
            println(e)
            return Ressource.Error(message = "An unknown error has occurred.")
        }
        return Ressource.Success(response)
    }

    suspend fun insert(wine: Wine) = wineDao.insert(wine)

    suspend fun delete(wine: Wine) = wineDao.delete(wine)

    suspend fun update(wine: Wine) = wineDao.update(wine)
}