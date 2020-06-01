package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.SearchResponseDto;
import com.roomoftruth.rot.dto.SearchSaveRequestDto;
import com.roomoftruth.rot.service.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/search/{num}")
	@ApiOperation("num으로 검색 기록 가져오기")
	public List<SearchResponseDto> getSearchesByNum(@PathVariable long num) {
		log.info("SearchController : getSearchesByNum / {}", num);

		return searchService.findByNum(num);
	}

	@PostMapping("/search")
	@ApiOperation("키워드 검색")
	public Long updateSearch(@RequestBody SearchSaveRequestDto requestDto) {
		log.info("InterestController : updateInterest / {}", requestDto.getNum());

		return searchService.search(requestDto);
	}

}
