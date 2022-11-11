package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.LoginUserRequest
import io.github.simonscholz.conduit.dto.v1.models.NewUserRequest
import io.github.simonscholz.conduit.dto.v1.models.User
import io.github.simonscholz.conduit.dto.v1.models.UserResponse
import io.github.simonscholz.core.application.user.RegisterUserUseCase
import io.smallrye.mutiny.Uni
import javax.annotation.security.PermitAll
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import org.keycloak.authorization.client.AuthzClient
import org.mapstruct.factory.Mappers

@Path("/api/users")
class UsersResource(
    private val registerUserUseCase: RegisterUserUseCase,
    private val authzClient: AuthzClient,
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    fun register(
        newUser: NewUserRequest,
    ): Uni<Response> {
        val user = userMapper.toDomain(newUser.user)
        return registerUserUseCase.registerUser(user).map {
            Response.status(201).entity(
                UserResponse(
                    User(
                        email = user.email,
                        password = user.password,
                        username = user.name,
                        bio = user.bio ?: "",
                        image = user.image ?: "",
                        token = "Bearer token",
                    ),
                ),
            ).build()
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/login")
    fun login(
        loginUserRequest: LoginUserRequest,
    ): Response = authzClient.obtainAccessToken(loginUserRequest.user.email, loginUserRequest.user.password).run {
        Response.ok(UserResponse(User("Max", "secret", "Max", "", "", "${this.tokenType} ${this.token}"))).build()
    }

    companion object {
        private val userMapper = Mappers.getMapper(UserMapper::class.java)
    }
}
