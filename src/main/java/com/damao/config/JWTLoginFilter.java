package com.damao.config;

import com.damao.entity.TUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huyongxing
 * @site www.skson.com
 * @date 2018/8/2 10:25
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    static final String JOE_CLAIM_SEGMENT = "{\"iss\":\"joe\",\r\n" + " \"exp\":1300819380,\r\n"
            + " \"http://example.com/is_root\":true}";

    static final String JOE_HEADER_RSA = "{\"alg\":\"RS256\"}";
    //    static final MacSigner hmac = new MacSigner(JwtSpecData.HMAC_KEY);

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // TODO 解析验证用户名密码，进行登录
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            TUser user = new ObjectMapper().readValue(request.getInputStream(), TUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //TODO 登录成功生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Jwt token = JwtHelper.encode(JOE_HEADER_RSA, new MacSigner("xfd"));
        response.setHeader("Authorization", "Bearer " + token.toString());
//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
