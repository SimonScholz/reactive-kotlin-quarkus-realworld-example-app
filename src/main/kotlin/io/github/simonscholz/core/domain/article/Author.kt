package io.github.simonscholz.core.domain.article

data class Author(
    val username: String,
    val bio: String,
    val image: String,
    val following: Boolean,
)
