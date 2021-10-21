package ch.kra.mycellar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.kra.mycellar.WineType

@Database(entities = arrayOf(Wine::class), version = 2)
abstract class WineRoomDatabase : RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        @Volatile
        private var INSTANCE: WineRoomDatabase? = null

        fun getDatabase(context: Context): WineRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WineRoomDatabase::class.java,
                    "wine_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance

                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //Create a new table with the wine_type as en integer (opposed to a string)
                database.execSQL(
                    "CREATE TABLE wine_new (id INTEGER NOT NULL DEFAULT null, wine_name TEXT NOT NULL DEFAULT null, offered_by TEXT NOT NULL, wine_type INTEGER NOT NULL DEFAULT null, quantity INTEGER NOT NULL DEFAULT null, PRIMARY KEY(id))"
                )
                //get the current stored wine
                val cursor = database.query(SimpleSQLiteQuery("SELECT * FROM wine"))
                //initialize the cursor with the 1st wine
                cursor.moveToNext()

                //for each wine in the database get the correct resource id from the english wine type and populate the new wine table
                while (!cursor.isAfterLast) {
                    val wineTypeString = cursor.getString(cursor.getColumnIndex("wine_type"))
                    val wineTypeInt = getWineFromEnglishString(wineTypeString)
                    val wineId = cursor.getInt(cursor.getColumnIndex("id"))
                    val wineName = cursor.getString(cursor.getColumnIndex("wine_name"))
                    val offeredBy = cursor.getString(cursor.getColumnIndex("offered_by"))
                    val quantity = cursor.getInt(cursor.getColumnIndex("quantity"))

                    val sqlCommand =
                        "INSERT INTO wine_new (id, wine_name, offered_by, wine_type, quantity) VALUES ($wineId, '$wineName', '$offeredBy', $wineTypeInt, $quantity)"
                    database.execSQL(sqlCommand)
                    cursor.moveToNext()
                }
                //remove the old wine table
                database.execSQL("DROP TABLE wine")
                //rename the new wine table
                database.execSQL("ALTER TABLE wine_new RENAME TO wine")
            }
        }

        private fun getWineFromEnglishString(wineTypeString: String): Int {
            return when (wineTypeString) {
                "Red wine" -> WineType.RED_WINE.resId
                "White wine" -> WineType.WHITE_WINE.resId
                "Rosé wine" -> WineType.ROSE_WINE.resId
                "Sparkling wine" -> WineType.SPARKLING_WINE.resId
                "Desert wine" -> WineType.DESSERT_WINE.resId
                "Fortified wine" -> WineType.FORTIFIED_WINE.resId
                else -> WineType.ALL.resId
            }
        }
    }
}