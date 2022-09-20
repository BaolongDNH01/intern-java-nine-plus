package com.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.model.AppUser;
import com.security.repository.IAppUserRepository;
import com.security.service.JwtUserDetailsService;
import com.security.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private IAppUserRepository appUserRepository;

    private final String SECRET;
    private final String PREFIX_VALUE;
    private final String[] PUBLIC_URL;
    private final Integer JWT_TOKEN_VALIDITY;

    public CustomAuthorizationFilter(String SECRET, String PREFIX_VALUE, String jwtTokenValidity, String[] publicUrl) {
        this.SECRET = SECRET;
        this.PREFIX_VALUE = PREFIX_VALUE;
        this.JWT_TOKEN_VALIDITY = Integer.valueOf(jwtTokenValidity);
        this.PUBLIC_URL = publicUrl;
    }

    private Boolean isPublicUrl(String path) {
        for (String string : PUBLIC_URL) {
            if (path.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
    	try {
    		if (isPublicUrl(request.getServletPath())) {
                filterChain.doFilter(request, response);
            } else {
                Cookie[] requestCookie = request.getCookies();
                Cookie accessCookie = null;
                Cookie refreshCookie = null;
                if (requestCookie != null) {
                    for (Cookie cookie : requestCookie) {
                        if (cookie.getName().equals(ConstantUtils.ACCESS_TOKEN)) {
                            accessCookie = cookie;
                        }
                        if (cookie.getName().equals(ConstantUtils.REFRESH_TOKEN)) {
                            refreshCookie = cookie;
                        }

                    }
                }
                if (accessCookie != null && request.getHeader(ConstantUtils.PREFIX_TOKEN) != null
                        && request.getHeader(ConstantUtils.PREFIX_TOKEN).startsWith(PREFIX_VALUE)) {
                    try {
                        String token = accessCookie.getValue();
                        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
                        JWTVerifier verifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = verifier.verify(token);
                        String username = decodedJWT.getSubject();
                        if (jwtUserDetailsService.loadUserByUsername(username) == null) {
                            throw new Exception();
                        }
                        String[] roles = decodedJWT.getClaim(ConstantUtils.ROLES).asArray(String.class);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        for (String role : roles) {
                            authorities.add(new SimpleGrantedAuthority(role));
                        }
                        UsernamePasswordAuthenticationToken authenToken = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenToken);
                        filterChain.doFilter(request, response);
                    } catch (Exception exception) {
                        response.setHeader(ConstantUtils.ERROR, exception.getMessage());
                        response.sendError(HttpStatus.FORBIDDEN.value());
                    }
                } else if (refreshCookie != null && request.getHeader(ConstantUtils.PREFIX_TOKEN).startsWith(PREFIX_VALUE)) {
                    try {
                        String token = refreshCookie.getValue();
                        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
                        JWTVerifier verifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = verifier.verify(token);
                        String username = decodedJWT.getSubject();
                        AppUser user = appUserRepository.getAppUserByUsername(username);
                        String accessToken = JWT.create()
                                .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + this.JWT_TOKEN_VALIDITY))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim(ConstantUtils.ROLES,
                                        new SimpleGrantedAuthority(ConstantUtils.ROLE_ADMIN)
                                                .getAuthority())
                                .sign(algorithm);
                        Cookie accessCookieN = new Cookie(ConstantUtils.ACCESS_TOKEN, accessToken);
                        accessCookieN.setHttpOnly(true);
                        accessCookieN.setSecure(false);
                        accessCookieN.setMaxAge(this.JWT_TOKEN_VALIDITY);
                        response.addCookie(refreshCookie);
                        response.addCookie(accessCookieN);
                        response.addHeader(ConstantUtils.PREFIX_TOKEN, PREFIX_VALUE);
                    } catch (Exception exception) {
                        response.setHeader(ConstantUtils.ERROR, exception.getMessage());
                        response.sendError(HttpStatus.FORBIDDEN.value());
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            }
		} catch (Exception exception) {
			response.setHeader(ConstantUtils.ERROR, exception.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value());
		}
    }
}
