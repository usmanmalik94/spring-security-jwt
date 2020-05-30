package com.springpractice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springpractice.domain.JwtRequest;
import com.springpractice.domain.JwtResponse;
import com.springpractice.service.MyUserDetailsService;
import com.springpractice.utils.JwtTokenUtil;

@RestController
@CrossOrigin
public class HelloWorldController {
	private AuthenticationManager authenticationManager;
	private MyUserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;

	public HelloWorldController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		return ResponseEntity.ok(new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(jwtRequest.getUsername()))));
	}

	@GetMapping(value = "/")
	public String helloEverybody() {
		return "Hello World!";
	}

	@GetMapping(value = "/user")
	public String helloUser() {
		return "Hello User!";
	}

	@GetMapping(value = "/admin")
	public String helloAdmin() {
		return "Hello Admin!";
	}
}
