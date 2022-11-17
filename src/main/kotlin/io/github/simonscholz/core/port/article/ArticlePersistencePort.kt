package io.github.simonscholz.core.port.article

import io.github.simonscholz.core.domain.User
import io.smallrye.mutiny.Uni

interface ArticlePersistencePort {
    fun findbyAuthor(email: String): Uni<User?>

    fun saveArticle(user: User): Uni<User>
}
