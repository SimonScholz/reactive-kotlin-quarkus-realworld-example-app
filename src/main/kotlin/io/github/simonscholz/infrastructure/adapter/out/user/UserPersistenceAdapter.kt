package io.github.simonscholz.infrastructure.adapter.out.user

import io.github.simonscholz.core.domain.User
import io.github.simonscholz.core.port.user.UserPersistencePort
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserPersistenceAdapter(
    private val userPanacheRepository: UserPanacheRepository,
) : UserPersistencePort {
    override fun findUserByEmail(email: String): Uni<User?> =
        userPanacheRepository.findByEmail(email).onItem().ifNotNull().transform(UserEntityMapper::toDomain)

    override fun saveUser(user: User): Uni<User> =
        userPanacheRepository.save(UserEntityMapper.toMongoEntity(user)).map(UserEntityMapper::toDomain)
}
