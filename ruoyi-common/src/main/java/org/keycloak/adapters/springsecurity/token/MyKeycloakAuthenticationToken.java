package org.keycloak.adapters.springsecurity.token;

import org.keycloak.MyKeycloakPrincipal;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author oblivion
 * @date 2022-06-26 20:38
 */
public class MyKeycloakAuthenticationToken extends KeycloakAuthenticationToken {

    private MyKeycloakPrincipal myKeycloakPrincipal;

    public void setPrincipal(MyKeycloakPrincipal myKeycloakPrincipal) {
        this.myKeycloakPrincipal = myKeycloakPrincipal;
    }

    @Override
    public Object getPrincipal() {
        return this.myKeycloakPrincipal;
    }

    public MyKeycloakAuthenticationToken(KeycloakAccount account, boolean interactive) {
        super(account, interactive);
    }

    public MyKeycloakAuthenticationToken(KeycloakAccount account, boolean interactive, Collection<? extends GrantedAuthority> authorities) {
        super(account, interactive, authorities);
    }
}
