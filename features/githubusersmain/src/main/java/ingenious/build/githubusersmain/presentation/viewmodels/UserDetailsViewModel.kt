package ingenious.build.githubusersmain.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ingenious.build.githubusersmain.data.repository.UserRepository
import ingenious.build.githubusersmain.domain.models.toUserDetails
import ingenious.build.githubusersmain.presentation.states.UserDetailsUIState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val userId: Int,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsUIState>(UserDetailsUIState.Loading)
    val uiState: StateFlow<UserDetailsUIState> = _uiState

    private val job = Job()

    init {
        viewModelScope.launch {
            userRepository.getUserDetails(accountId = userId).catch {
                _uiState.update {
                    UserDetailsUIState.Error(it.toString())
                }
            }.stateIn(
                scope = this,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            ).collect { userDetails ->
                if (userDetails != null) {
                    _uiState.update {
                        UserDetailsUIState.Success(userDetails.toUserDetails())
                    }
                }
            }
        }
        loadData()
    }

    fun onEvent(event: UserDetailsViewModelEvent) {
        when (event) {
            is UserDetailsViewModelEvent.RefreshUsers -> loadData()
        }
    }

    private fun loadData() {
        if (job.isActive) {
            job.cancelChildren()
        }
        viewModelScope.launch(job + CoroutineExceptionHandler { _, throwable ->
            when (_uiState.value) {
                is UserDetailsUIState.Loading, is UserDetailsUIState.Error ->
                    _uiState.update {
                        UserDetailsUIState.Error(throwable.toString())
                    }

                else -> { /* do nothing */ }
            }
        }) {
            userRepository.refreshUserDetailData(userId = userId)
        }
    }

    sealed class UserDetailsViewModelEvent {
        data object RefreshUsers : UserDetailsViewModelEvent()
    }
}
