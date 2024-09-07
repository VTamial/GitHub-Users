package ingenious.build.githubusersmain.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import ingenious.build.githubusersmain.data.repository.UserRepository
import ingenious.build.githubusersmain.domain.models.toUser
import kotlinx.coroutines.flow.map

class UsersViewModel(
   private val userRepository: UserRepository,
) : ViewModel() {
    var userPagingFlow = userRepository.getUsersPager().flow
        .map { pagingData ->
            pagingData.map { it.toUser() }
        }
        .cachedIn(viewModelScope)
}