package io.github.simonscholz.core.port.user

import io.github.simonscholz.core.domain.User
import io.smallrye.mutiny.Uni

interface UserPersistencePort {
    fun findUserByEmail(email: String): Uni<User?>

    fun saveUser(user: User): Uni<User>
}
