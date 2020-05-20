package com.rot.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rot.model.Ranking;
import com.rot.service.IRankingService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RankingController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private IRankingService rankingService;

	@GetMapping("/ranking")
	@ApiOperation("랭킹 가져오기")
	public List<Ranking> getRanking() {
		logger.info("GET : /api/ranking");

		List<Ranking> result = rankingService.getRanking();
		
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getAPicture() == null || result.get(i).getAPicture().equals("null")) {
				result.get(i).setAPicture("images/agent_default.png");
				continue;
			}
			result.get(i).setAPicture("images/" + result.get(i).getAPicture());
		}
		
		Collections.sort(result, new Comparator<Ranking>() {

			@Override
			public int compare(Ranking o1, Ranking o2) {
				
				if(o1.getPoint() == o2.getPoint()) {
					return o1.getName().compareTo(o2.getName());
				}
				
				return o2.getPoint() - o1.getPoint();
			}
		});
		
		return result;
	}

}
