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

import com.webtech.blogpost.blogPost.dto.CommentsDto;
import com.webtech.blogpost.blogPost.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	private ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
		commentService.createComment(commentsDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable("postId") Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByPost(postId));
	}

	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable("userName") String userName) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(userName));
	}

}
