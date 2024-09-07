package ingenious.build.githubusersmain.di

import ingenious.build.githubusersmain.data.repository.UserRepository
import ingenious.build.githubusersmain.data.repository.UserRepositoryImpl
import ingenious.build.githubusersmain.presentation.viewmodels.UserDetailsViewModel
import ingenious.build.githubusersmain.presentation.viewmodels.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gitHubUserModule = module {
    viewModel<UsersViewModel> {
        UsersViewModel(get())
    }
    viewModel { (userId: Int) -> UserDetailsViewModel(userId, get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}