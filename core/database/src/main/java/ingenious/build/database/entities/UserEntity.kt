package ingenious.build.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String,
    val type: String,
    val siteAdmin: Boolean,
    val name: String? = null,
    val email: String? = null,
    val starredAt: String? = null,
)

