package io.github.simonscholz.core.port

import io.github.simonscholz.core.domain.User
import io.smallrye.mutiny.Uni

interface UserPersistencePort {
    fun findUserById(email: String): Uni<User?>

    fun saveUser(user: User): Uni<User>
}
