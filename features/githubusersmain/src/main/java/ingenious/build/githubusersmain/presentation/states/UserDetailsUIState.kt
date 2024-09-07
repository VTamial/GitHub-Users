package ingenious.build.githubusersmain.presentation.states

import ingenious.build.githubusersmain.domain.models.UserDetails

sealed class UserDetailsUIState {
    class Success(val users: UserDetails) : UserDetailsUIState()
    class Error(val message: String) : UserDetailsUIState()
    data object Loading : UserDetailsUIState()
}