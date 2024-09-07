package ingenious.build.githubusersmain.domain.models

import ingenious.build.common.utils.convertToReadable
import ingenious.build.database.entities.UserDetailsEntity

data class UserDetails(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val type: String,
    val siteAdmin: Boolean,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean?,
    val bio: String?,
    val twitterUsername: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    val createdAt: String,
    val updatedAt: String,
)

fun UserDetailsEntity.toUserDetails() = UserDetails(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    type = type,
    siteAdmin = siteAdmin,
    name = name,
    company = company,
    blog = blog,
    location = location,
    email = email,
    hireable = hireable,
    bio = bio,
    twitterUsername = twitterUsername,
    publicRepos = publicRepos,
    publicGists = publicGists,
    followers = followers,
    following = following,
    createdAt = createdAt.convertToReadable(),
    updatedAt = updatedAt.convertToReadable(),
)
