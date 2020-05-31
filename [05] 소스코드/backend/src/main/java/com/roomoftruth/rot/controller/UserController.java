package com.roomoftruth.rot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.dto.UserUpdateRequestDto;
import com.roomoftruth.rot.jwt.IJwtService;
import com.roomoftruth.rot.service.IKaKaoAPIService;
import com.roomoftruth.rot.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IKaKaoAPIService kakaoAPIService;

	@Autowired
	private IJwtService jwtService;

	@Autowired
	private IUserService userService;

	@PostMapping("/kakaologin")
	@ApiOperation("카카오 로그인")
	public String login(@RequestParam String access_Token) {
		logger.info("UserController : login");

		JsonNode json = kakaoAPIService.getKaKaoUserInfo(access_Token);

		String result = null;

		try {
			result = kakaoAPIService.redirectToken(json); // 토큰 발행
		} catch (Exception e) {
			logger.error(e + "");
		}

		return result;
	}

	@PostMapping("/loginToken")
	@ApiOperation("토큰 검증")
	public Object token(@RequestParam String access_token) {
		logger.info("UserController : token");

		Object result = null;

		if (jwtService.isUsable(access_token)) {
			result = jwtService.get("user");
		}
		logger.info(result.toString());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/{num}")
	@ApiOperation("num으로 유저 정보 가져오기")
	public UserResponseDto getUserByNum(@PathVariable long num) {
		logger.info("UserController : getUserByNum / {}", num);
		UserResponseDto user = userService.findByNum(num);

		return user;
	}
	

	@PatchMapping("/user")
	@ApiOperation("유저 정보 수정")
	public String updateUser(@RequestBody UserUpdateRequestDto updateRequestDto) {
		logger.info("UserController : updateUser / {}" + updateRequestDto.getNum());

		userService.save(updateRequestDto);

		UserResponseDto userResponseDto = userService.findByNum(updateRequestDto.getNum());

		String jwt = jwtService.create("user", userResponseDto, "user");

		return jwt;
	}

}
