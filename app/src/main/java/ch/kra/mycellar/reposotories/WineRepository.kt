package ch.kra.mycellar.reposotories

import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.database.WineDao
import ch.kra.mycellar.util.Ressource

class WineRepository(
    private val wineDao: WineDao
) : IWineRepository {
    override fun getAllWine() = wineDao.getAll()

    override fun getWineByType(wineType: Int) = wineDao.getByWineType(wineType = wineType)

    override suspend fun getWine(wineId: Int): Ressource<Wine> {
        val response = try {
            wineDao.getWine(wineId = wineId)
        } catch (e: Exception) {
            println(e)
            return Ressource.Error(message = "An unknown error has occurred.")
        }
        return Ressource.Success(response)
    }

    override suspend fun insert(wine: Wine) = wineDao.insert(wine)

    override suspend fun delete(wine: Wine) = wineDao.delete(wine)

    override suspend fun update(wine: Wine) = wineDao.update(wine)
}