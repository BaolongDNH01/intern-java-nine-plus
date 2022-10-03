package com.nineplus.pharmacy.config.filter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nineplus.pharmacy.constant.CommonConstants;

@PropertySource("classpath:application.properties")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private String PREFIX_TOKEN;

	private String SECRET_KEY;

	private int JWT_EXPIRATION;

	private AuthenticationManager authenticationManager;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManage, String secretKey, String prefixToken,
			String jwtAge) {
		this.authenticationManager = authenticationManage;
		this.SECRET_KEY = secretKey;
		this.PREFIX_TOKEN = prefixToken;
		this.JWT_EXPIRATION = Integer.parseInt(jwtAge);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String requestData = "";
		try {
			requestData = request.getReader().lines().collect(Collectors.joining());
		} catch (IOException ex) {

		}
		JSONObject loginInfor = new JSONObject(requestData);
		String username = loginInfor.getString(CommonConstants.Authentication.USERNAME);
		String password = loginInfor.getString(CommonConstants.Authentication.PASSWORD);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = authenticationManager.authenticate(authToken);
		
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim(CommonConstants.Authentication.ROLES,
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refreshToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000))
				.withIssuer(request.getRequestURL().toString()).sign(algorithm);
		Cookie accessCookie = new Cookie(CommonConstants.Authentication.ACCESS_COOKIE, accessToken);
		accessCookie.setHttpOnly(true);
		accessCookie.setSecure(false);
		accessCookie.setMaxAge(JWT_EXPIRATION * 1000);
		Cookie refreshCookie = new Cookie(CommonConstants.Authentication.REFRESH_COOKIE, refreshToken);
		refreshCookie.setHttpOnly(true);
		refreshCookie.setSecure(false);
		refreshCookie.setMaxAge(JWT_EXPIRATION * 1000);
		response.addCookie(refreshCookie);
		response.addCookie(accessCookie);
		response.addHeader(CommonConstants.Authentication.PREFIX_TOKEN, PREFIX_TOKEN);
	}

}
