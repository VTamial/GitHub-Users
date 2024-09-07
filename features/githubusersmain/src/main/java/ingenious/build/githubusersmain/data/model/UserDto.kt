package ingenious.build.githubusersmain.data.model

import ingenious.build.database.entities.UserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserResponse(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val url: String,
    val type: String,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
    val name: String? = null,
    val email: String? = null,
    val starredAt: String? = null,
)

fun GitHubUserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatarUrl,
        url = this.url,
        type = this.type,
        siteAdmin = this.siteAdmin,
        name = this.name,
        email = this.email,
        starredAt = this.starredAt
    )
}
