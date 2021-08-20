package ch.kra.mycellar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Wine::class), version = 1)
abstract class WineRoomDatabase: RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        @Volatile
        private var INSTANCE: WineRoomDatabase? = null

        fun getDatabase(context: Context): WineRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WineRoomDatabase::class.java,
                    "wine_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}