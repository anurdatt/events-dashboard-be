package com.tcsonline.eventsdashboard.interceptors;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tcsonline.eventsdashboard.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final String secret;
	
	private final String apiBaseUrl;
	
	private static Set<String> exceptionRoutes;
	 
	@Autowired
	UserService userService;

	@Autowired
	public JwtInterceptor(@Value("${jwt.secret}") String secret,
			@Value("${api.base-url}") String apiBaseUrl) {
		this.secret = secret;
		this.apiBaseUrl = apiBaseUrl;
		
		exceptionRoutes = Arrays
				.asList(this.apiBaseUrl + "/v1/users/authenticate")
				.stream()
				.collect(Collectors.toSet());
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.err.println(request.getRequestURI());
		if (exceptionRoutes.contains(request.getRequestURI()) || 
				!request.getRequestURI().startsWith(apiBaseUrl + "/v1") ||
				request.getMethod().equalsIgnoreCase("OPTIONS")) {
			return HandlerInterceptor.super.preHandle(request, response, handler);
		}
		
		String auth = request.getHeader("Authorization");
		if(auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			String usernameFromToken = null;
			Date expirationFromToken = null;
			try {
				usernameFromToken = getClaimFromToken(token, Claims::getSubject);
				expirationFromToken = getClaimFromToken(token, Claims::getExpiration);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			} catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
				System.out.println("JWT Token corrupt or invalid");
			}
			
			if (usernameFromToken == null || expirationFromToken == null ||
					userService.findAllUsersByUsername(usernameFromToken).isEmpty() ||
					expirationFromToken.before(new Date())) {
				System.out.println("User not found or JWT Token expired!");
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "Request not authorized!");
				return false;
			}
			return HandlerInterceptor.super.preHandle(request, response, handler);
		}
		else {
			System.out.println("Authorization Header is not found in request!");
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Request not authorized!");
			return false;
		}
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
		return claimsResolver.apply(claims);
	}
}
