package io.github.simonscholz.core.port

import io.github.simonscholz.core.domain.User

interface UserAuthPort {
    fun registerUserAuth(user: User)
}
