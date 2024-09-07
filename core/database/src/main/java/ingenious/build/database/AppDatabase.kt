package ingenious.build.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ingenious.build.database.dao.UserDao
import ingenious.build.database.dao.UserDetailsDao
import ingenious.build.database.entities.UserDetailsEntity
import ingenious.build.database.entities.UserEntity

@Database(
    entities = [UserEntity::class, UserDetailsEntity::class], version = 1, exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailsDao(): UserDetailsDao
}
