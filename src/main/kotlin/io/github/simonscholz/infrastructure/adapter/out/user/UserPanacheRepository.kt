package io.github.simonscholz.infrastructure.adapter.out.user

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserPanacheRepository : ReactivePanacheMongoRepository<UserMongoEntity> {
    fun findByEmail(email: String) = find("email", email).firstResult()

    fun save(userMongoEntity: UserMongoEntity) = persist(userMongoEntity)
}
