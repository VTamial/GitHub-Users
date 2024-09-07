package ingenious.build.githubusers

import android.app.Application
import ingenious.build.di.dataBaseModule
import ingenious.build.di.gitHubApiModule
import ingenious.build.githubusersmain.di.gitHubUserModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(dataBaseModule, gitHubApiModule, gitHubUserModule))
        }
    }
}