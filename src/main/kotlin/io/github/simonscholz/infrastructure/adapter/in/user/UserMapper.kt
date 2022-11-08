package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.User
import org.mapstruct.Mapper

@Mapper
interface UserMapper {

    fun toDomain(user: User): io.github.simonscholz.core.domain.User
}
