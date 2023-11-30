package com.webtech.blogpost.blogPost.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webtech.blogpost.blogPost.dto.VoteDto;
import com.webtech.blogpost.blogPost.exception.BlogPostException;
import com.webtech.blogpost.blogPost.exception.PostNotFoundException;
import com.webtech.blogpost.blogPost.model.Post;
import com.webtech.blogpost.blogPost.model.Vote;
import com.webtech.blogpost.blogPost.model.VoteType;
import com.webtech.blogpost.blogPost.repository.PostRepository;
import com.webtech.blogpost.blogPost.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	public void vote(VoteDto voteDto) {

		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(
				() -> new PostNotFoundException("Post Not Found with ID:" + voteDto.getPostId().toString()));

		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new BlogPostException("You have already " + voteDto.getVoteType() + "'d for this post");
		}
		if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}

}
