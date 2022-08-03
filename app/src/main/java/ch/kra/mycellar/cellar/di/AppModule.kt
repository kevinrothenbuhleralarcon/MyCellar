package ch.kra.mycellar.cellar.di

import android.content.Context
import androidx.room.Room
import ch.kra.mycellar.cellar.data.local.WineRoomDatabase
import ch.kra.mycellar.cellar.data.local.dao.WineDao
import ch.kra.mycellar.cellar.data.repository.WineRepositoryImpl
import ch.kra.mycellar.cellar.domain.repository.IWineRepository
import ch.kra.mycellar.core.DefaultDispatchers
import ch.kra.mycellar.core.DispatcherProvider
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
        .addMigrations(WineRoomDatabase.MIGRATION_3_4)
        .build()

    @Singleton
    @Provides
    fun provideWineDao(db: WineRoomDatabase) = db.wineDao()

    @Singleton
    @Provides
    fun provideWineRepository(wineDao: WineDao): IWineRepository {
        return WineRepositoryImpl(wineDao)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider {
        return DefaultDispatchers()
    }
}