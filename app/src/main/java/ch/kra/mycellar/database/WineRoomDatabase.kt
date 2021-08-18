package ch.kra.mycellar.database

import androidx.room.RoomDatabase

abstract class WineRoomDatabase: RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        @Volatile
        private var INSTANCE: WineRoomDatabase? = null
    }
}