package io.github.simonscholz.infrastructure.adapter.`in`.user

import io.github.simonscholz.conduit.dto.v1.models.User
import io.github.simonscholz.conduit.dto.v1.models.UserResponse
import io.quarkus.security.identity.SecurityIdentity
import javax.ws.rs.GET
import javax.ws.rs.Path
import org.jboss.resteasy.reactive.NoCache

@Path("/api/user")
class UserResource(
    private val securityIdentity: SecurityIdentity,
) {

    @GET
    @NoCache
    fun me(): UserResponse =
        UserResponse(User(securityIdentity.principal.name, securityIdentity.principal.name, securityIdentity.principal.name, securityIdentity.principal.name, securityIdentity.principal.name, securityIdentity.credentials.toString()))
}
