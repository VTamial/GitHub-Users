package ingenious.build.github_api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.appendPathSegments

class ApiService(private val httpClient: HttpClient) {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val USERS = "users"
        private const val SINCE = "?since="
    }

    suspend fun getUsers(from: Int? = null): HttpResponse {
        return httpClient.get(BASE_URL + USERS + (from?.let { SINCE + it } ?: ""))
    }

    suspend fun getUserInfo(accountId: Int) = httpClient.get(BASE_URL) {
        url {
            appendPathSegments(USERS, accountId.toString())
        }
    }
}