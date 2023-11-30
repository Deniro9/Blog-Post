package com.webtech.blogpost.blogPost.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webtech.blogpost.blogPost.model.Post;
import com.webtech.blogpost.blogPost.model.User;
import com.webtech.blogpost.blogPost.model.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
