package io.github.simonscholz.infrastructure.adapter.out.user

import io.github.simonscholz.core.domain.User
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface IUserEntityMapper {
    fun toMongoEntity(user: User): UserMongoEntity

    fun toDomain(userMongoEntity: UserMongoEntity?): User?
}

object UserEntityMapper : IUserEntityMapper by Mappers.getMapper(IUserEntityMapper::class.java)
