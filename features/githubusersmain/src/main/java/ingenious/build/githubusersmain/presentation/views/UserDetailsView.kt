package ingenious.build.githubusersmain.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ingenious.build.githubusersmain.R
import ingenious.build.githubusersmain.domain.models.UserDetails
import ingenious.build.githubusersmain.presentation.states.UserDetailsUIState
import ingenious.build.githubusersmain.presentation.theme.GitHubUserTheme
import ingenious.build.githubusersmain.presentation.viewmodels.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parameterSetOf

@Composable
fun UserDetailsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    userId: Int,
) {
    val viewModel = koinViewModel<UserDetailsViewModel> { parameterSetOf(userId) }
    val state = viewModel.uiState.collectAsState()
    Box(modifier = modifier.padding(16.dp)) {
        when (val uiState = state.value) {
            is UserDetailsUIState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uiState.message, color = Color.White)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { viewModel.onEvent(UserDetailsViewModel.UserDetailsViewModelEvent.RefreshUsers) }) {
                            Text("Refresh")
                        }
                        Button(onClick = { onBackClick.invoke() }) {
                            Text("Go back")
                        }
                    }
                }
            }

            is UserDetailsUIState.Loading -> Text(text = "Loading", color = Color.White)
            is UserDetailsUIState.Success -> {
                UserDetailsView(userDetails = uiState.users, onBackClick = onBackClick)
            }
        }
    }
}

@Composable
private fun UserDetailsView(
    modifier: Modifier = Modifier,
    userDetails: UserDetails,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 75.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .clip(RoundedCornerShape(10))
                .background(Color.White)
                .fillMaxSize()
                .padding(vertical = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = userDetails.name ?: "No name provided",
                fontStyle = FontStyle.Italic,
                fontSize = 28.sp
            )
            if (userDetails.bio != null) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(RoundedCornerShape(10))
                        .background(Color.White)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(10)),
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center,
                        text = userDetails.bio,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Following", modifier = Modifier, fontWeight = FontWeight.Bold)
                    Text(text = userDetails.following.toString(), modifier = Modifier)
                }

                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Followers", modifier = Modifier, fontWeight = FontWeight.Bold)
                    Text(text = userDetails.followers.toString(), modifier = Modifier)
                }
            }

            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Company: ${userDetails.company ?: "No company provided"}",
                    fontWeight = FontWeight.Bold
                )
                Row(
                    Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Created date:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = userDetails.createdAt,
                        modifier = Modifier.padding(horizontal = 5.dp),
                    )
                }


                HorizontalDivider(
                    modifier = Modifier.padding(20.dp),
                    color = Color.Black,
                    thickness = 1.dp,
                )
            }

            Row(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Public Repo",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = userDetails.publicRepos.toString(), modifier = Modifier)
                }

                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Public Gists",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = userDetails.publicGists.toString(), modifier = Modifier)
                }
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userDetails.avatarUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(y = (-75).dp)
                .padding(10.dp)
                .clip(CircleShape)
                .size(150.dp)
                .align(Alignment.TopCenter),
            contentDescription = null,
            placeholder = painterResource(R.drawable.placeholder)
        )

        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = { onBackClick.invoke() },
            colors = ButtonColors(
                contentColor = Color.Black,
                containerColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(text = "OKAY")
        }

    }
}

@Preview
@Composable
private fun UserDetailsScreenPreview() {
    GitHubUserTheme {
        UserDetailsView(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp),
            userDetails = UserDetails(
                id = 158675,
                login = "6",
                avatarUrl = "https://avatars.githubusercontent.com/u/158675?v=4",
                type = "User",
                siteAdmin = false,
                name = "philip",
                company = "@wealthsimple",
                blog = "", // No blog provided
                location = "Maine",
                email = null, // Email is null
                hireable = null, // Hireable is null
                bio = "This was not hard though! Now let’s check out how our screens will look like.",// Bio is null
                twitterUsername = null, // Twitter username is null
                publicRepos = 147,
                publicGists = 7,
                followers = 225,
                following = 359,
                createdAt = "2009-11-26T21:48:59Z",
                updatedAt = "2024-07-02T19:55:46Z"
            ), {}
        )
    }
}