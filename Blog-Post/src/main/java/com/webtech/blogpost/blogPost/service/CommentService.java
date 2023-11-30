package com.webtech.blogpost.blogPost.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webtech.blogpost.blogPost.dto.CommentsDto;
import com.webtech.blogpost.blogPost.exception.PostNotFoundException;
import com.webtech.blogpost.blogPost.mapper.CommentMapper;
import com.webtech.blogpost.blogPost.model.Comment;
import com.webtech.blogpost.blogPost.model.NotificationEmail;
import com.webtech.blogpost.blogPost.model.Post;
import com.webtech.blogpost.blogPost.model.User;
import com.webtech.blogpost.blogPost.repository.CommentRepository;
import com.webtech.blogpost.blogPost.repository.PostRepository;
import com.webtech.blogpost.blogPost.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	private final UserRepository userRepository;
	private final CommentMapper commentMapper;
	private final MailService mailService;
	private final MailContentBuilder mailContentBuilder;

	public void createComment(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder
				.build(post.getUser().getUsername() + " posted a comment on your post." + post.getUrl());
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<CommentsDto> getCommentByPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getCommentsByUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}
}
