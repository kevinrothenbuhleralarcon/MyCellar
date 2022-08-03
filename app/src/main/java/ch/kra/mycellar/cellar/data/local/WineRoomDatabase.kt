package ch.kra.mycellar.cellar.data.local

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.kra.mycellar.cellar.data.local.converter.WineTypeConverter
import ch.kra.mycellar.cellar.data.local.dao.WineDao
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType

@Database(entities = [Wine::class], version = 4, exportSchema = false)
@TypeConverters(WineTypeConverter::class)
abstract class WineRoomDatabase : RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            @SuppressLint("Range")
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

        val MIGRATION_2_3 = object : Migration(2, 3) {
            @SuppressLint("Range")
            override fun migrate(database: SupportSQLiteDatabase) {
                //get the current stored wine
                val cursor = database.query(SimpleSQLiteQuery("SELECT * FROM wine"))
                //initialize the cursor with the 1st wine
                cursor.moveToNext()

                while (!cursor.isAfterLast) {
                    val wineTypeOld = cursor.getInt(cursor.getColumnIndex("wine_type"))
                    val wineTypeNew = getNewTypeValuesFromOldOnes(wineTypeOld)
                    val wineId = cursor.getInt(cursor.getColumnIndex("id"))

                    val sqlCommand =
                        "UPDATE wine SET wine_type = $wineTypeNew WHERE id = $wineId"
                    database.execSQL(sqlCommand)
                    cursor.moveToNext()
                }
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            @SuppressLint("Range")
            override fun migrate(database: SupportSQLiteDatabase) {
                //Create a new table with the wine_type as a WineType (opposed to a Int)
                database.execSQL(
                    "CREATE TABLE wine_new (id INTEGER NOT NULL, wine_name TEXT NOT NULL, offered_by TEXT, wine_type TEXT NOT NULL, quantity INTEGER NOT NULL, PRIMARY KEY(id))"
                )
                //get the current stored wine
                val cursor = database.query(SimpleSQLiteQuery("SELECT * FROM wine"))
                //initialize the cursor with the 1st wine
                cursor.moveToNext()

                //for each wine in the database get the correct resource id from the english wine type and populate the new wine table
                while (!cursor.isAfterLast) {
                    val wineTypeInt = cursor.getInt(cursor.getColumnIndex("wine_type"))
                    val wineType = getWineTypeFromInt(wineTypeInt)
                    val wineId = cursor.getInt(cursor.getColumnIndex("id"))
                    val wineName = cursor.getString(cursor.getColumnIndex("wine_name"))
                    val offeredBy = cursor.getString(cursor.getColumnIndex("offered_by"))
                    val quantity = cursor.getInt(cursor.getColumnIndex("quantity"))

                    val sqlCommand =
                        "INSERT INTO wine_new (id, wine_name, offered_by, wine_type, quantity) VALUES ($wineId, '$wineName', '$offeredBy', '${wineType.name}', $quantity)"
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

            private fun getNewTypeValuesFromOldOnes(wineTypeOld: Int): Int {
                return when (wineTypeOld) {
                    2131689588 -> WineType.RED_WINE.resId
                    2131689595 -> WineType.WHITE_WINE.resId
                    2131689589 -> WineType.ROSE_WINE.resId
                    2131689592 -> WineType.SPARKLING_WINE.resId
                    else -> WineType.RED_WINE.resId
                }
            }

            private fun getWineTypeFromInt(wineTypeOld: Int): WineType {
                return when(wineTypeOld) {
                    1 -> WineType.RED_WINE
                    2 -> WineType.WHITE_WINE
                    3 -> WineType.ROSE_WINE
                    4 -> WineType.SPARKLING_WINE
                    5 -> WineType.DESSERT_WINE
                    6 -> WineType.FORTIFIED_WINE
                    else -> WineType.RED_WINE
                }
            }
        }
    }