package com.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.utils.ConstantUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private final AuthenticationManager authenticationManager;

    private final String SECRET;
    private final String PREFIX_VALUE;
    private final Integer JWT_TOKEN_VALIDITY;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String secret, String prefixValue,
                                      String jwtTokenValidity) {
        this.authenticationManager = authenticationManager;
        this.SECRET = secret;
        this.PREFIX_VALUE = prefixValue;
        this.JWT_TOKEN_VALIDITY = Integer.valueOf(jwtTokenValidity);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        UsernamePasswordAuthenticationToken
                authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(this.SECRET.getBytes());
        String accessToken = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.JWT_TOKEN_VALIDITY))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(ConstantUtils.ROLES,
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.JWT_TOKEN_VALIDITY))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        generateCookie(response, refreshToken, ConstantUtils.REFRESH_TOKEN);
        generateCookie(response, accessToken, ConstantUtils.ACCESS_TOKEN);
        response.addHeader(ConstantUtils.PREFIX_TOKEN, this.PREFIX_VALUE);
    }

    private void generateCookie(HttpServletResponse response, String token, String cookieName) {
        Cookie refreshCookie = new Cookie(cookieName, token);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setMaxAge(this.JWT_TOKEN_VALIDITY);
        response.addCookie(refreshCookie);
    }
}
