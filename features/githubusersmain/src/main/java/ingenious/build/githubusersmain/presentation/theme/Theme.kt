package ingenious.build.githubusersmain.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext


@Composable
fun GitHubUserTheme(
    content: @Composable () -> Unit
) {
   KoinContext {
        MaterialTheme(
            colorScheme = darkColorScheme(),
            typography = Typography,
            content = content
        )
   }
}