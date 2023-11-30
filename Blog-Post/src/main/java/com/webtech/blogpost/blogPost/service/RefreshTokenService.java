package com.webtech.blogpost.blogPost.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webtech.blogpost.blogPost.exception.BlogPostException;
import com.webtech.blogpost.blogPost.model.RefreshToken;
import com.webtech.blogpost.blogPost.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());

		return refreshTokenRepository.save(refreshToken);

	}

	public void validateRefreshToken(String refreshToken) {
		refreshTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new BlogPostException("Invalid Refresh Token!"));

	}

	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteByToken(refreshToken);

	}

}
