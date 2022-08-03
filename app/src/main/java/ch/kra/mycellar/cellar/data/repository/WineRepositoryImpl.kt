package ch.kra.mycellar.cellar.data.repository

import ch.kra.mycellar.cellar.data.local.dao.WineDao
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.domain.repository.WineRepository
import ch.kra.mycellar.util.Resource
import kotlinx.coroutines.flow.first

class WineRepositoryImpl(
    private val wineDao: WineDao
) : WineRepository {
    override fun getAllWine() = wineDao.getAll()

    override fun getWineByType(wineType: WineType) = wineDao.getByWineType(wineType = wineType.name)

    //override fun getWine(wineId: Int) = wineDao.getWine(wineId = wineId)

    override suspend fun getWine(wineId: Int): Resource<Wine> {
        val response = try {
            wineDao.getWine(wineId = wineId).first()
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