package ingenious.build.di

import androidx.room.Room
import ingenious.build.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().userDetailsDao() }
}