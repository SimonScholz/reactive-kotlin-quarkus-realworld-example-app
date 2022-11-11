package io.github.simonscholz.core.domain

data class User(
    val name: String,
    val email: String,
    val password: String,
    val bio: String? = null,
    val image: String? = null,
)
