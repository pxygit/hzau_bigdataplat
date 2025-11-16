package com.ruoyi.framework.security.filter;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.MyKeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.adapters.springsecurity.token.MyKeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author oblivion
 * @date 2022-06-26 19:33
 */
@Component
public class AuthenticatedUserFilter extends OncePerRequestFilter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${keycloak.resource:}")
    private String resource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Object object = SecurityUtils.getAuthentication().getPrincipal();
        if (object instanceof KeycloakPrincipal){
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityUtils.getAuthentication().getPrincipal();
            MyKeycloakPrincipal myKeycloakPrincipal = new MyKeycloakPrincipal(keycloakPrincipal.getName(), keycloakPrincipal.getKeycloakSecurityContext());
            String userName = "";
            Map<String, AccessToken.Access> resourceAccess = keycloakPrincipal.getKeycloakSecurityContext().getToken().getResourceAccess();
            if (resourceAccess!=null && resourceAccess.get(resource)!=null && CollectionUtils.isNotEmpty(resourceAccess.get(resource).getRoles())){
                userName = resourceAccess.get(resource).getRoles().iterator().next();
            } else {
                userName = keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername();
            }
            if (myKeycloakPrincipal.getLoginUser() == null) {
                LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(userName);
                myKeycloakPrincipal.setLoginUser(loginUser);
            }
            KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityUtils.getAuthentication();
            MyKeycloakAuthenticationToken myKeycloakAuthenticationToken = new MyKeycloakAuthenticationToken(keycloakAuthenticationToken.getAccount(),
                    keycloakAuthenticationToken.isInteractive(), keycloakAuthenticationToken.getAuthorities());
            myKeycloakAuthenticationToken.setPrincipal(myKeycloakPrincipal);
            SecurityContextHolder.getContext().setAuthentication(myKeycloakAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
