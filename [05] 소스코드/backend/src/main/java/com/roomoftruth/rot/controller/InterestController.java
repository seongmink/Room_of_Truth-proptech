package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.InterestResponseDto;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import com.roomoftruth.rot.jwt.JwtService;
import com.roomoftruth.rot.service.InterestService;
import com.roomoftruth.rot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class InterestController {

	private static final Logger logger = LoggerFactory.getLogger(InterestController.class);

	@Autowired
	private InterestService interestService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@GetMapping("/interest/{num}")
	@ApiOperation("num으로 선호도 가져오기")
	public InterestResponseDto getInterestByNum(@PathVariable long num) {
		logger.info("InterestController : getInterestByNum / {}", num);
		InterestResponseDto interest = interestService.findByNum(num);

		System.out.println("interest = " + interest);

		return interest;
	}

	@PatchMapping("/interest")
	@ApiOperation("유저 선호도 수정")
	public String updateInterest(@RequestBody InterestSaveRequestDto requestDto) {
		logger.info("InterestController : updateInterest / {}", requestDto.getUserNum());

		if(interestService.save(requestDto) == 0) {
			return "failed";
		} else {
			return "success";
		}
	}

}
