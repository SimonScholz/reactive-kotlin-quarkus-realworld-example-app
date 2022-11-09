package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.LoginUserRequest
import io.github.simonscholz.conduit.dto.v1.models.NewUserRequest
import io.github.simonscholz.conduit.dto.v1.models.User
import io.github.simonscholz.conduit.dto.v1.models.UserResponse
import javax.annotation.security.PermitAll
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import org.keycloak.admin.client.Keycloak
import org.keycloak.authorization.client.AuthzClient
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation

@Path("/api/users")
class UsersResource(
    private val keycloak: Keycloak,
    private val authzClient: AuthzClient,
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    fun register(
        newUser: NewUserRequest,
    ): Response {
        val userRepresentation = UserRepresentation().apply {
            email = newUser.user.email
            username = newUser.user.email
            isEnabled = true
            realmRoles = listOf("user")
            credentials = listOf(
                CredentialRepresentation().apply {
                    value = newUser.user.password
                    type = CredentialRepresentation.PASSWORD
                    isTemporary = false
                },
            )
        }
        return keycloak.realm("quarkus").users().create(userRepresentation).run {
            when (this.status) {
                201 -> Response.status(201).entity(UserResponse(User(newUser.user.email, newUser.user.password, newUser.user.username, "", "", ""))).build()
                else -> this
            }
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
}
