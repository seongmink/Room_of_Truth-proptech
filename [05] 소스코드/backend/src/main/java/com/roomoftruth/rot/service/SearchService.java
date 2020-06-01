package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.SearchResponseDto;
import com.roomoftruth.rot.dto.SearchSaveRequestDto;
import com.roomoftruth.rot.repository.SearchRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

	private final SearchRepository searchRepository;
	private final UserRepository userRepository;

	public List<SearchResponseDto> findByNum(Long num) {
		List<Search> temp = searchRepository.findByNum(num);

		List<SearchResponseDto> searches =  new ArrayList<>();

		for (Search search : temp) {
			searches.add(new SearchResponseDto(search));
		}

		Collections.sort(searches, new Comparator<SearchResponseDto>() {
			@Override
			public int compare(SearchResponseDto o1, SearchResponseDto o2) {
				return o2.getUpdatedAt().compareTo(o1.getUpdatedAt());
			}
		});

		return searches;
	}

	public Long search(SearchSaveRequestDto requestDto) {

		Search search = searchRepository.findByNumAndKeyword(requestDto.getNum(), requestDto.getKeyword());


		if (search == null) {
			User user = userRepository.findByNum(requestDto.getNum())
					.orElseThrow(() -> new IllegalArgumentException("정보가 없어염~~"));

			search = searchRepository.save(requestDto.toEntity(user));
		} else {
			search.logined(LocalDateTime.now());
		}

		return search.getSearchId();
	}

}