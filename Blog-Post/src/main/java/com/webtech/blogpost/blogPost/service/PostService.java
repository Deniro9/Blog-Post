package com.webtech.blogpost.blogPost.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webtech.blogpost.blogPost.dto.PostRequest;
import com.webtech.blogpost.blogPost.dto.PostResponse;
import com.webtech.blogpost.blogPost.exception.PostNotFoundException;
import com.webtech.blogpost.blogPost.exception.SubredditNotFoundException;
import com.webtech.blogpost.blogPost.mapper.PostMapper;
import com.webtech.blogpost.blogPost.model.Post;
import com.webtech.blogpost.blogPost.model.Subreddit;
import com.webtech.blogpost.blogPost.model.User;
import com.webtech.blogpost.blogPost.repository.PostRepository;
import com.webtech.blogpost.blogPost.repository.SubredditRepository;
import com.webtech.blogpost.blogPost.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostService {
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final UserRepository userRepository;

	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		return postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
	}

	public List<PostResponse> getAllPosts() {
		log.info("Get All Posts Start");
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		return postRepository.findAllBySubreddit(subreddit).stream().map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}

}
