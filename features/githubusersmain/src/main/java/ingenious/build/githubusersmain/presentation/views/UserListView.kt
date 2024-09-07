package ingenious.build.githubusersmain.presentation.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ingenious.build.githubusersmain.R
import ingenious.build.githubusersmain.domain.models.User
import ingenious.build.githubusersmain.presentation.theme.GitHubUserTheme
import ingenious.build.githubusersmain.presentation.viewmodels.UsersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListScreen(toUserDetailsScreen: (Int) -> Unit) {
    Scaffold { innerPadding ->
        koinViewModel<UsersViewModel>()
        val viewModel = koinViewModel<UsersViewModel>()
        val pagingItems = viewModel.userPagingFlow.collectAsLazyPagingItems()
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Loading", color = White)
                }
            }

            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Is Error: " +(pagingItems.loadState.append as LoadState.NotLoading),
                            modifier = Modifier.padding(innerPadding),
                            color = White
                        )
                        Button(onClick = { pagingItems.retry() }) {
                            Text(text = "Retry")
                        }
                    }
                }

            }

            else -> {
                UserListView(toUserDetailsScreen, pagingItems)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListView(toUserDetailsScreen: (Int) -> Unit, pagingItems: LazyPagingItems<User>) {
    val listState = rememberLazyListState()
    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }
    val scope = rememberCoroutineScope()
    val pullRefresh = rememberPullToRefreshState()


    Scaffold(
        modifier = Modifier.pullToRefresh(
            isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
            state = pullRefresh,
            onRefresh = { pagingItems.refresh() }
        )
    ) { innerPadding ->
        if (pagingItems.itemCount != 0) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(color = Black),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState,
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id },
                ) { index ->
                    val item = pagingItems[index]
                    if (item != null) {
                        UserCardView(user = item, onClick = toUserDetailsScreen)
                    }
                }
            }
            AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
                GoToTop { scope.launch { listState.animateScrollToItem(0) } }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No data", color = White)
                Button(onClick = { pagingItems.refresh() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
fun UserCardView(user: User, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(5)),
        onClick = { onClick(user.id) },
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically),
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder)
            )

            Column(
                Modifier.fillMaxWidth()
            ) {
                Text(text = user.login, modifier = Modifier.padding(10.dp))
                Text(text = user.url, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun GoToTop(goToTop: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp)
                .align(Alignment.BottomEnd),
            onClick = goToTop,
            contentColor = Black,
            containerColor = White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_top),
                contentDescription = "go to top"
            )
        }
    }
}

@Preview
@Composable
private fun UserCardViewPreview() {
    GitHubUserTheme {
        UserCardView(
            user = User(
                id = 1,
                login = "mojombo",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                url = "https://api.github.com/users/mojombo",
                type = "User",
                siteAdmin = false
            )
        ) {}
    }

}