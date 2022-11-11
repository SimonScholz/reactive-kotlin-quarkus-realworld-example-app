package io.github.simonscholz.infrastructure.adapter.out.user

import io.github.simonscholz.core.domain.User
import io.github.simonscholz.core.port.UserAuthPort
import javax.enterprise.context.ApplicationScoped
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation

@ApplicationScoped
class KeyCloakAdapter(
    private val keycloak: Keycloak,
) : UserAuthPort {
    override fun registerUserAuth(user: User) {
        val userRepresentation = UserRepresentation().apply {
            email = user.email
            username = user.email
            isEnabled = true
            realmRoles = listOf("user")
            credentials = listOf(
                CredentialRepresentation().apply {
                    value = user.password
                    type = CredentialRepresentation.PASSWORD
                    isTemporary = false
                },
            )
        }
        keycloak.realm("quarkus").users().create(userRepresentation)
    }
}
