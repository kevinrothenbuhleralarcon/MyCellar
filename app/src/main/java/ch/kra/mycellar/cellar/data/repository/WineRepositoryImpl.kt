package ch.kra.mycellar.cellar.data.repository

import ch.kra.mycellar.cellar.data.local.dao.WineDao
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.domain.repository.IWineRepository
import ch.kra.mycellar.util.Resource

class WineRepositoryImpl(
    private val wineDao: WineDao
) : IWineRepository {
    override fun getAllWine() = wineDao.getAll()

    override fun getWineByType(wineType: WineType) = wineDao.getByWineType(wineType = wineType.name)

    override suspend fun getWine(wineId: Int): Resource<Wine> {
        val response = try {
            wineDao.getWine(wineId = wineId)
        } catch (e: Exception) {
            println(e)
            return Resource.Error(message = "An unknown error has occurred.")
        }
        return Resource.Success(response)
    }

    override suspend fun insert(wine: Wine) = wineDao.insert(wine)

    override suspend fun delete(wine: Wine) = wineDao.delete(wine)

    override suspend fun update(wine: Wine) = wineDao.update(wine)
}