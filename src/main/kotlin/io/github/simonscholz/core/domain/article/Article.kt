package io.github.simonscholz.core.domain.article

import io.github.simonscholz.core.domain.Tag
import java.time.Instant

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
