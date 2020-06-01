package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

	@Autowired
	private SearchService searchService;

	public List<Search> findByNum(Long num) {
//		List<Search> search = searchService.findByNum(num);
//
//		return new SearchResponseDto(search);

		return null;
	}

}
