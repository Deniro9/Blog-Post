package com.webtech.blogpost.blogPost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtech.blogpost.blogPost.model.Post;
import com.webtech.blogpost.blogPost.model.Subreddit;
import com.webtech.blogpost.blogPost.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);

}
