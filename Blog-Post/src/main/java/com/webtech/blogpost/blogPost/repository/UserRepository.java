package com.webtech.blogpost.blogPost.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webtech.blogpost.blogPost.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
