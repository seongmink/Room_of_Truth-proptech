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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

	private final SearchRepository searchRepository;
	private final UserRepository userRepository;

	public List<SearchResponseDto> findByNum(Long num) {

		User user = userRepository.findByNum(num);
		List<Search> temp = searchRepository.findByUser(user);

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

//	public List<String> getAddress(String keyword) {
//
//		List<Search> temp = searchRepository.findAddress(keyword);
//		List<String> result = new ArrayList<>();
//
//		for (int i = 0; i < temp.size(); i++) {
//			result.add(temp.get(i).getKeyword());
//		}
//
//		return result;
//	}

	@Transactional
	public Long search(SearchSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());

		Search search = searchRepository.findByUserAndKeyword(user, requestDto.getKeyword());

		if (search == null) {
			search = searchRepository.save(requestDto.toEntity(user));
		} else {
			search.updateTime(LocalDateTime.now());
		}

		return search.getSearchId();
	}

	@Transactional
	public Long deleteSearch(long id) throws Exception {

		searchRepository.deleteById(id);

		return id;
	}

}