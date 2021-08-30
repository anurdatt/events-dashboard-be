package com.tcsonline.eventsdashboard.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcsonline.eventsdashboard.entities.User;
import com.tcsonline.eventsdashboard.exceptions.user.InvalidPasswordException;
import com.tcsonline.eventsdashboard.exceptions.user.UserNotFoundException;
import com.tcsonline.eventsdashboard.repositories.UserRepository;
import com.tcsonline.eventsdashboard.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Value("${jwt.secret}") 
	private String secret;
	
	private static class AuthenticatonRequestUser {
		String username;
		String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	}
	private class AuthenticationResponseUser extends User {
		private String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		
	}
	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping()
	public List<User> getUsers() {
		return service.findAllUsers();
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<Object> authenticate(@RequestBody AuthenticatonRequestUser reqUser) {
		List<User> users = service.findAllUsersByUsername(reqUser.username);
		if (users.isEmpty()) {
			//return new ResponseEntity<Object>("User not found!", HttpStatus.NOT_FOUND);
			throw new UserNotFoundException();
		}
	
		User user = users.get(0);
		
		if(passwordEncoder.matches(reqUser.password, user.getPassword())) {
			AuthenticationResponseUser resUser = new AuthenticationResponseUser();
			resUser.setId(user.getId());
			resUser.setUsername(user.getUsername());
			resUser.setFirstName(user.getFirstName());
			resUser.setLastName(user.getLastName());
			
			String token = Jwts.builder()
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
			
			resUser.setToken(token);
			
			return new ResponseEntity<Object>(resUser, HttpStatus.OK);
		}
		
		//return new ResponseEntity<Object>("Invalid password!", HttpStatus.FORBIDDEN);
		throw new InvalidPasswordException();
	}
	
}
