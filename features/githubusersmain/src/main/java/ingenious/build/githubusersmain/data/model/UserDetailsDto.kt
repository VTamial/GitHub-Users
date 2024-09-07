package ingenious.build.githubusersmain.data.model

import ingenious.build.database.entities.UserDetailsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserDetailsResponse(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val type: String,
    @SerialName("site_admin")
    val siteAdmin: Boolean,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val bio: String?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    @SerialName("public_repos")
    val publicRepos: Int,
    @SerialName("public_gists")
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)


fun GitHubUserDetailsResponse.toUserDetailsEntity() = UserDetailsEntity(
    id = id,
    login = this.login,
    avatarUrl = this.avatarUrl,
    type = this.type,
    siteAdmin = this.siteAdmin,
    name = this.name,
    company = this.company,
    blog = this.blog,
    location = this.location,
    email = this.email,
    hireable = this.hireable,
    bio = this.bio,
    twitterUsername = this.twitterUsername,
    publicRepos = this.publicRepos,
    publicGists = this.publicGists,
    followers = this.followers,
    following = this.following,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)