package ch.kra.mycellar.di

import android.content.Context
import androidx.room.Room
import ch.kra.mycellar.database.WineDao
import ch.kra.mycellar.database.WineRoomDatabase
import ch.kra.mycellar.reposotories.IWineRepository
import ch.kra.mycellar.reposotories.WineRepository
import ch.kra.mycellar.util.DefaultDispatchers
import ch.kra.mycellar.util.DispatcherProvider
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
        .addMigrations(WineRoomDatabase.MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun provideWineDao(db:WineRoomDatabase) = db.wineDao()

    @Singleton
    @Provides
    fun provideWineRepository(wineDao: WineDao): IWineRepository {
        return WineRepository(wineDao)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider {
        return DefaultDispatchers()
    }
}