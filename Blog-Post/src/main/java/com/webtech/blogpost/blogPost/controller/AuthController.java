package com.webtech.blogpost.blogPost.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtech.blogpost.blogPost.dto.AuthenticationResponse;
import com.webtech.blogpost.blogPost.dto.LoginRequest;
import com.webtech.blogpost.blogPost.dto.RefreshTokenRequest;
import com.webtech.blogpost.blogPost.dto.RegisterRequest;
import com.webtech.blogpost.blogPost.service.AuthService;
import com.webtech.blogpost.blogPost.service.RefreshTokenService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;

	@PostMapping(value = "/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest request) {
		authService.signup(request);
		return new ResponseEntity<>("User Registration Successfull!", HttpStatus.OK);
	}

	@GetMapping(value = "/accountVerification/{token}")
	public ResponseEntity<String> accountVerificaton(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<String>("Account Activated Sucessfully!", HttpStatus.OK);
	}

	@PostMapping(value = "/login")
	public AuthenticationResponse login(@RequestBody LoginRequest request) {
		log.info("Request::::::" + request);
		AuthenticationResponse authenticationResponse = authService.login(request);
		log.info("Response:::::::::" + authenticationResponse);
		return authenticationResponse;
	}

	@PostMapping("refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	}
}
