package com.ruoyi.web.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakAdminClient {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak-rustful-api.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak-rustful-api.admin.client-secret}")
    private String adminClientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取 Keycloak Admin Access Token（客户端凭证模式）
     */
    public String getAdminAccessToken() {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", adminClientId);
        params.add("client_secret", adminClientSecret);
        params.add("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to obtain admin access token from Keycloak");
        }

        return (String) response.getBody().get("access_token");
    }

    /**
     * 通过用户名获取keycloak中用户ID
     */
    @SuppressWarnings("unchecked")
    public String getUserIdByUsername(String username) {
        String accessToken = getAdminAccessToken();
        String usersUrl = authServerUrl + "/admin/realms/" + realm + "/users?username=" + username + "&exact=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(usersUrl, HttpMethod.GET, request, List.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().isEmpty()) {
            throw new RuntimeException("User not found in Keycloak");
        }

        Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
        return (String) user.get("id");
    }

    /**
     * 修改keycloak中用户密码
     */
    public void changePassword(String userId, String newPassword) {
        String accessToken = getAdminAccessToken();
        String resetPasswordUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        Map<String, Object> passwordPayload = new HashMap<>();
        passwordPayload.put("type", "password");
        passwordPayload.put("value", newPassword);
        passwordPayload.put("temporary", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(passwordPayload, headers);
        ResponseEntity<Void> response = restTemplate.exchange(resetPasswordUrl, HttpMethod.PUT, request, Void.class);

        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Failed to change password in Keycloak");
        }
    }

    /**
     * 验证Keycloak用户密码是否正确,通过用户名和密码尝试获取Token,获取到token即表明密码正确
     * @param username
     * @param password
     * @return
     */
    public boolean validateKCPasswordByUsername(String username, String password) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", adminClientId);
        params.add("client_secret", adminClientSecret);
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            // 如果能拿到 access_token，说明密码正确
            return response.getStatusCode() == HttpStatus.OK && response.getBody() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
