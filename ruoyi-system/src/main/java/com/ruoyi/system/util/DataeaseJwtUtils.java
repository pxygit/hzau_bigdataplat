package com.ruoyi.system.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import com.ruoyi.common.exception.DataEaseException;
import com.ruoyi.system.dataease.domain.TokenInfo;
import com.ruoyi.system.dataease.domain.TokenInfo.TokenInfoBuilder;
import com.ruoyi.system.dataease.domain.OnlineUserModel;

public class DataeaseJwtUtils {

    @Value("${token.expireTime:480}")
    private static Long expireTime;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, TokenInfo tokenInfo, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        Verification verification = JWT.require(algorithm)
                .withClaim("username", tokenInfo.getUsername())
                .withClaim("userId", tokenInfo.getUserId());
        JWTVerifier verifier = verification.build();

        verifySign(algorithm, token);
        verifier.verify(token);
        return true;
    }

    public static void verifySign(Algorithm algorithm, String token) {
        DecodedJWT decode = JWT.decode(token);
        algorithm.verify(decode);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static TokenInfo tokenInfoByToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("username").asString();
        Long userId = jwt.getClaim("userId").asLong();
        Long exp = jwt.getClaim("exp").asLong();  // pxy 2025-05-09 增加
        if (StringUtils.isEmpty(username) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(exp)) {
            DataEaseException.throwException("token格式错误！");
        }
        TokenInfoBuilder tokenInfoBuilder = TokenInfo.builder().username(username).userId(userId).exp(exp);
        return tokenInfoBuilder.build();
    }

    /**
     * @param tokenInfo 用户信息
     * @param secret    用户的密码
     * @return 加密的token
     */
    public static String sign(TokenInfo tokenInfo, String secret) {
        Long userId = tokenInfo.getUserId();

        long expireTimeMillis = getExpireTime();
        Date date = new Date(System.currentTimeMillis() + expireTimeMillis);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Builder builder = JWT.create()
                .withClaim("username", tokenInfo.getUsername())
                .withClaim("userId", userId);
        return builder.withExpiresAt(date).sign(algorithm);
    }

    public static Long getExpireTime() {
        if (expireTime == null) {
            expireTime = 480L; // 默认480分钟
        }
        return expireTime * 60000L;
    }

    private static boolean tokenValid(OnlineUserModel model) {
        String token = model.getToken();
        Long loginTime = model.getLoginTime();
        long expireTimeMillis = getExpireTime();
        // 如果当前时间减去登录时间小于超时时间则说明token未过期 返回有效状态
        return System.currentTimeMillis() - loginTime < expireTimeMillis;

    }
}
