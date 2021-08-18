package ch.kra.mycellar.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Query("SELECT * FROM wine ORDER BY wine_type, wine_name ASC")
    fun getAll(): Flow<List<Wine>>

    @Query("SELECT * FROM wine WHERE wine_type = :wineType ORDER BY wine_type, wine_name ASC")
    fun getByWineType(wineType: String): Flow<List<Wine>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wine: Wine)

    @Delete
    suspend fun delete(wine: Wine)

    @Update
    suspend fun update(wine: Wine)
}