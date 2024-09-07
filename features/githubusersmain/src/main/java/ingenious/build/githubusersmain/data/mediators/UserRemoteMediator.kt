package ingenious.build.githubusersmain.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ingenious.build.database.AppDatabase
import ingenious.build.database.entities.UserEntity
import ingenious.build.github_api.ApiService
import ingenious.build.githubusersmain.data.model.GitHubUserResponse
import ingenious.build.githubusersmain.data.model.toUserEntity
import io.ktor.client.call.body
import java.net.UnknownHostException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: 1
                }
            }
            val users = apiService.getUsers(loadKey).body<List<GitHubUserResponse>>()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.userDao().clearAll()
                }
                val usersEntities = users.map { it.toUserEntity() }
                appDatabase.userDao().insertAll(usersEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = users.isEmpty()
            )
        }
        catch (e: UnknownHostException) {
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}