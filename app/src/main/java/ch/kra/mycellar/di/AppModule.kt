package ch.kra.mycellar.di

import android.content.Context
import androidx.room.Room
import ch.kra.mycellar.database.WineRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWineDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        WineRoomDatabase::class.java,
        "wine_database"
    )
        .addMigrations(WineRoomDatabase.MIGRATION_1_2)
        .build()

    @Singleton
    @Provides
    fun provideWineDao(db:WineRoomDatabase) = db.wineDao()
}