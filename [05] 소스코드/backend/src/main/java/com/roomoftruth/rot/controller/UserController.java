package com.roomoftruth.rot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserInfoSaveRequestDto;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.jwt.JwtService;
import com.roomoftruth.rot.service.KakaoAPIService;
import com.roomoftruth.rot.service.UserService;
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
	private KakaoAPIService kakaoAPIService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@PostMapping("/user/kakaologin")
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

	@PostMapping("/user/logintoken")
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
	public User getUserByNum(@PathVariable long num) {
		logger.info("UserController : getUserByNum / {}", num);
		User user = userService.findByNum(num);

		return user;
	}

	@PatchMapping("/user/info")
	@ApiOperation("유저 정보 수정")
	public String updateUser(@RequestBody UserInfoSaveRequestDto requestDto) {
		logger.info("UserController : updateUser / {}", requestDto.getNum());

		userService.save(requestDto);

		UserResponseDto userResponseDto = userService.findByNum(requestDto.getNum());

		String jwt = jwtService.create("user", userResponseDto, "user");

		return jwt;
	}

}
