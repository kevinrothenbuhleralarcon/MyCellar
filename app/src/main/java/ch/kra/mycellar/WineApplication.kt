package ch.kra.mycellar

import android.app.Application
import ch.kra.mycellar.database.WineRoomDatabase

class WineApplication: Application() {
    val database: WineRoomDatabase by lazy { WineRoomDatabase.getDatabase(this) }
}