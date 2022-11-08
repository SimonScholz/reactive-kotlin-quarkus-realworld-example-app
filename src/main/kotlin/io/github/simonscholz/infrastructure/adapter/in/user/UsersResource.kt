package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.NewUserRequest
import io.github.simonscholz.conduit.dto.v1.models.User
import io.github.simonscholz.conduit.dto.v1.models.UserResponse
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import javax.annotation.security.PermitAll
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/users")
class UsersResource(
    private val keycloak: Keycloak,
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
            username = newUser.user.username
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
                else -> Response.serverError().build()
            }
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/roles")
    fun getRoles(): List<Role> {
        return keycloak.realm("quarkus").roles().list().map {
            Role(it.name)
        }
    }
}

data class Role(val name: String)
