package ingenious.build.githubusersmain.data.repository

import androidx.paging.Pager
import ingenious.build.database.entities.UserDetailsEntity
import ingenious.build.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPager(): Pager<Int, UserEntity>
    suspend fun getUserDetails(accountId: Int): Flow<UserDetailsEntity>
    suspend fun refreshUserDetailData(userId: Int)
}