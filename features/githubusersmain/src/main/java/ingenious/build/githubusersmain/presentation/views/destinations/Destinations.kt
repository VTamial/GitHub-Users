package ingenious.build.githubusersmain.presentation.views.destinations

sealed class Destinations(val route: String) {
    data object UserInfo : Destinations("user_info")
    data object UserDetails : Destinations("user_detail")

    fun withArg(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}