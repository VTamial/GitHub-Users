package ingenious.build.githubusersmain.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ingenious.build.githubusersmain.presentation.theme.GitHubUserTheme
import ingenious.build.githubusersmain.presentation.views.UserDetailsScreen
import ingenious.build.githubusersmain.presentation.views.UserListScreen
import ingenious.build.githubusersmain.presentation.views.destinations.Destinations

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUserTheme {
                val navController = rememberNavController()
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = Destinations.UserInfo.route,
                ) {
                    composable(route = Destinations.UserInfo.route) {
                        UserListScreen { userId ->
                            navController.navigate(Destinations.UserDetails.withArg(userId.toString()))
                        }
                    }

                    dialog(
                        route = Destinations.UserDetails.route + "/{userId}",
                        arguments = listOf(
                            navArgument("userId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) { entry ->
                        val userId = entry.arguments?.getInt("userId") ?: -1
                        UserDetailsScreen(
                            Modifier,
                            onBackClick = { navController.popBackStack() },
                            userId,
                        )
                    }
                }
            }
        }
    }
}
