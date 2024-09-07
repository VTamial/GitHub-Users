package ingenious.build.githubusersmain.domain.models

import ingenious.build.database.entities.UserEntity

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String,
    val type: String,
    val siteAdmin: Boolean,
    val name: String? = null,
    val email: String? = null,
    val starredAt: String? = null,
)

fun UserEntity.toUser() = User(
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