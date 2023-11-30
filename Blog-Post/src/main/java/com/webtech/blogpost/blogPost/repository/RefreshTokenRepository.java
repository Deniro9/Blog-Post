package com.webtech.blogpost.blogPost.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webtech.blogpost.blogPost.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByToken(String refreshToken);

}
