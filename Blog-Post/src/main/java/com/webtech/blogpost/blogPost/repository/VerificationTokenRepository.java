package com.webtech.blogpost.blogPost.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webtech.blogpost.blogPost.model.VerificationToken;


@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

}
