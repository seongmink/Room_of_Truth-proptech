package com.rot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rot.model.Search;
import com.rot.service.ISearchService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private ISearchService searchService;

	@PostMapping("/search")
	@ApiOperation(value = "키워드 검색")
	public String search(@RequestBody Search search) throws Exception {
		logger.info("GET : /search");

		if (search.getNum() != 0) {
			if (searchService.isSearched(search) == null) {
				searchService.search(search);
			} else {
				searchService.updateSearch(search);
			}
		}

		return "success";
	}

	@GetMapping("/search/{num}")
	@ApiOperation(value = "최근 검색")
	public List<Search> getAllSearch(@PathVariable(required = true) long num) throws Exception {
		logger.info("GET : /search/{num} : " + num);

		List<Search> list = searchService.getAllSearch(num);

		return list;
	}

	@DeleteMapping("/search/{num}/{keyword}")
	@ApiOperation(value = "검색 삭제")
	public String deleteSearch(@PathVariable(required = true) long num, @PathVariable(required = true) String keyword)
			throws Exception {
		logger.info("DELETE : /search/{num}/{keyword} : " + num + " " + keyword);

		Search search = new Search();
		search.setNum(num);
		search.setKeyword(keyword);

		if (searchService.deleteSearch(search) == 0) {
			return "failed";
		}

		return "success";
	}

}
