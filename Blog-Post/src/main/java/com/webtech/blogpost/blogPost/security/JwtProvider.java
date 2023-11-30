package com.webtech.blogpost.blogPost.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.webtech.blogpost.blogPost.exception.BlogPostException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new BlogPostException("Exception occurred while loading keystore", e);
		}

	}

	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).signWith(getPrivateKey())
				.compact();
	}

	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new BlogPostException("Exception occured while retrieving public key from keystore", e);
		}
	}

	public boolean validateToken(String jwt) {
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;

	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			throw new BlogPostException("Exception occured while retrieving public key from keystore", e);
		}
	}

	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}

	public String generateTokenWithUserName(String userName) {

		return Jwts.builder().setSubject(userName).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis))).signWith(getPrivateKey())
				.compact();

	}
}
