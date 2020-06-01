package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.InterestResponseDto;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import com.roomoftruth.rot.jwt.JwtService;
import com.roomoftruth.rot.service.InterestService;
import com.roomoftruth.rot.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class InterestController {

	private final InterestService interestService;
	private final JwtService jwtService;
	private final UserService userService;

	@GetMapping("/interest/{num}")
	@ApiOperation("num으로 관심 정보 가져오기")
	public InterestResponseDto getInterestByNum(@PathVariable long num) {
		log.info("InterestController : getInterestByNum / {}", num);
		InterestResponseDto interest = interestService.findByNum(num);

		System.out.println("interest = " + interest);

		return interest;
	}

	@PatchMapping("/interest")
	@ApiOperation("유저 관심 정보 수정")
	public String updateInterest(@RequestBody InterestSaveRequestDto requestDto) {
		log.info("InterestController : updateInterest / {}", requestDto.getUserNum());

		if(interestService.save(requestDto) == 0) {
			return "failed";
		} else {
			return "success";
		}
	}

}
