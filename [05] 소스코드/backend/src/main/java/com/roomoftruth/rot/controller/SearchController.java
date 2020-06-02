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


//	@GetMapping("/search/{keyword}")
//	@ApiOperation("주소 검색")
//	public List<String> getAddress(@PathVariable String keyword) {
//		log.info("SearchController : getAddress / {}", num);
//
//		List<String> list = searchService.getAddress(keyword);
//
//		return list;
//	}

	@PostMapping("/search")
	@ApiOperation("검색 기록 추가")
	public Long updateSearch(@RequestBody SearchSaveRequestDto requestDto) {
		log.info("SearchController : updateSearch / {}", requestDto.getUserNum());

		return searchService.search(requestDto);
	}

	@DeleteMapping("/search/{id}")
	@ApiOperation(value = "검색 기록 삭제")
	public Long deleteSearch(@PathVariable(required = true) long id) throws Exception {
		log.info("SearchController : deleteSearch / {}", id);

		return searchService.deleteSearch(id);
	}

}
