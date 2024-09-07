package ingenious.build.githubusersmain.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import ingenious.build.database.AppDatabase
import ingenious.build.github_api.ApiService
import ingenious.build.githubusersmain.data.mediators.UserRemoteMediator
import ingenious.build.githubusersmain.data.model.GitHubUserDetailsResponse
import ingenious.build.githubusersmain.data.model.toUserDetailsEntity
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPager() = Pager(
        config = PagingConfig(pageSize = 30),
        remoteMediator = UserRemoteMediator(apiService, appDatabase),
        pagingSourceFactory = {
            appDatabase.userDao().getAllUsers()
        }
    )

    override suspend fun getUserDetails(accountId: Int) =
        appDatabase.userDetailsDao().getUserDetails(accountId.toString())

    override suspend fun refreshUserDetailData(userId: Int) {
        withContext(Dispatchers.IO) {
            val apiResponse = apiService.getUserInfo(userId)
            Log.d("TAG", "refreshUserDetailData: $apiResponse")
            if (apiResponse.status.value == SUCCESS) {
                val userDetailsEntity =
                    apiResponse.body<GitHubUserDetailsResponse>().toUserDetailsEntity()
                appDatabase.userDetailsDao().insertUserDetails(userDetailsEntity)
            } else {
                throw Exception(apiResponse.status.description)
            }
        }
    }

    companion object {
        private const val SUCCESS = 200
    }
}