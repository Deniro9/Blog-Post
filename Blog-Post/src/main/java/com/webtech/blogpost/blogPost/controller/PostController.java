package com.webtech.blogpost.blogPost.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtech.blogpost.blogPost.dto.PostRequest;
import com.webtech.blogpost.blogPost.dto.PostResponse;
import com.webtech.blogpost.blogPost.service.PostService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/post")
@AllArgsConstructor
@Slf4j
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		log.info("POSTS::" + postService.getAllPosts());
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}

	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}

	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(name));
	}
}
