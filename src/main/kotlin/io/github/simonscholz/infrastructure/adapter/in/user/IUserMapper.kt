package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.NewUser as NewUserDTO
import io.github.simonscholz.conduit.dto.v1.models.User as UserDTO
import io.github.simonscholz.core.domain.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface IUserMapper {

    @Mapping(source = "username", target = "name")
    fun toDomain(user: UserDTO): User

    @Mapping(source = "username", target = "name")
    fun toDomain(user: NewUserDTO): User

    @Mapping(source = "name", target = "username")
    fun toDto(user: User): UserDTO
}

object UserMapper : IUserMapper by Mappers.getMapper(IUserMapper::class.java)
