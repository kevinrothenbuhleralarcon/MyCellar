package ch.kra.mycellar.cellar.data.local.dao

import androidx.room.*
import ch.kra.mycellar.cellar.data.local.entity.Wine
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Query("SELECT * FROM wine ORDER BY wine_type, wine_name ASC")
    fun getAll(): Flow<List<Wine>>

    @Query("SELECT * FROM wine WHERE wine_type = :wineType ORDER BY wine_type, wine_name ASC")
    fun getByWineType(wineType: String): Flow<List<Wine>>

    @Query("SELECT * FROM wine WHERE id = :wineId")
    fun getWine(wineId: Int): Flow<Wine>

    /*@Query("SELECT * FROM wine WHERE id = :wineId")
    suspend fun getWine(wineId: Int): Wine*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wine: Wine)

    @Delete
    suspend fun delete(wine: Wine)

    @Update
    suspend fun update(wine: Wine)
}