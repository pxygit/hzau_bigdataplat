package org.keycloak;

import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * @author oblivion
 * @date 2022-06-26 19:03
 */
public class MyKeycloakPrincipal<T extends KeycloakSecurityContext> extends KeycloakPrincipal<T> {

    private LoginUser loginUser;

    public MyKeycloakPrincipal(String name, T context) {
        super(name, context);
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }


}
