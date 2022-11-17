package io.github.simonscholz.core.domain.article

import java.time.Instant

typealias Tag = String

data class Article(
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<Tag>,
    val createdAt: Instant,
    val updatedAt: Instant,
    val favorited: Boolean,
    val favoritesCount: Int,
    val author: Author,
)
