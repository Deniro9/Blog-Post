package com.webtech.blogpost.blogPost.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webtech.blogpost.blogPost.dto.SubredditDto;
import com.webtech.blogpost.blogPost.exception.BlogPostException;
import com.webtech.blogpost.blogPost.mapper.SubredditMapper;
import com.webtech.blogpost.blogPost.model.Subreddit;
import com.webtech.blogpost.blogPost.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;

	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());

	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new BlogPostException("No Reddit found with ID:" + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
