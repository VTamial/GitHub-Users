package ingenious.build.di

import ingenious.build.github_api.ApiService
import ingenious.build.github_api.httpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val gitHubApiModule = module {

    single<HttpClient> { httpClient }

    single<ApiService> {
        ApiService(get())
    }
}