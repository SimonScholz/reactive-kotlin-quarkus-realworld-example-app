package io.github.simonscholz.infrastructure.adapter.out.user

import io.quarkus.mongodb.panache.common.MongoEntity

@MongoEntity(collection = "user")
data class UserMongoEntity(
    val name: String,
    val email: String,
    val password: String,
    val bio: String? = null,
    val image: String? = null,
)
