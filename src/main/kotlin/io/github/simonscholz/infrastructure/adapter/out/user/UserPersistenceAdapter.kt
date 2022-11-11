package io.github.simonscholz.infrastructure.adapter.out.user

import io.github.simonscholz.core.domain.User
import io.github.simonscholz.core.port.UserPersistencePort
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserPersistenceAdapter : UserPersistencePort {
    override fun findUserById(email: String): Uni<User?> {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User): Uni<User> {
        TODO("Not yet implemented")
    }
}
