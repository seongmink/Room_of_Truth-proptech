package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.SearchResponseDto;
import com.roomoftruth.rot.dto.SearchSaveRequestDto;
import com.roomoftruth.rot.repository.SearchRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

	private final SearchRepository searchRepository;
	private final UserRepository userRepository;

	public List<SearchResponseDto> findByNum(Long num) {

		User user = userRepository.findByNum(num);
		List<Search> temp = searchRepository.findTop5ByUserOrderByUpdatedAtDesc(user);

		List<SearchResponseDto> searches =  new ArrayList<>();

		for (Search search : temp) {
			searches.add(new SearchResponseDto(search));
		}

		return searches;
	}

	@Transactional
	public Long search(SearchSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());

		if(user == null)
			return 0L;

		Search search = searchRepository.findByUserAndKeyword(user, requestDto.getKeyword());

		if (search == null) {
			search = searchRepository.save(requestDto.toEntity(user));
		} else {
			search.updateTime(LocalDateTime.now());
		}

		return search.getSearchId();
	}

	@Transactional
	public Long deleteSearch(long id) {

		searchRepository.deleteById(id);

		return id;
	}

}