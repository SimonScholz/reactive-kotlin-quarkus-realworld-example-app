package io.github.simonscholz.core.application.user

import io.github.simonscholz.core.domain.User
import io.github.simonscholz.core.port.UserAuthPort
import io.github.simonscholz.core.port.UserPersistencePort
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RegisterUserUseCase(
    private val userAuthPort: UserAuthPort,
    private val userPersistencePort: UserPersistencePort,
) {
    fun registerUser(user: User): Uni<User> {
        userAuthPort.registerUserAuth(user)
        return userPersistencePort.saveUser(user)
    }
}
