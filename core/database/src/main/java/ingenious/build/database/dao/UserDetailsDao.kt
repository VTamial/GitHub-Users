package ingenious.build.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ingenious.build.database.entities.UserDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(userDetails: UserDetailsEntity)

    @Query("SELECT * FROM user_details WHERE login = :userId")
    fun getUserDetails(userId: String): Flow<UserDetailsEntity>
}