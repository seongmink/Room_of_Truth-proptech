package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.jwt.IJwtService;
import com.roomoftruth.rot.service.IKaKaoAPIService;
import com.roomoftruth.rot.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IKaKaoAPIService kakaoAPIService;

	@Autowired
	private IJwtService JwtService;

	@Autowired
	private IUserService userService;

	@PostMapping("/kakaologin")
	@ApiOperation("카카오 로그인")
	public String login(@RequestParam String access_Token) {
		logger.info("POST : /api/kakaologin");

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
		logger.info("POST : /api/loginToken");

		Object result = null;

		if (JwtService.isUsable(access_token)) {
			result = JwtService.get("user");
		}
		logger.info(result.toString());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/{num}")
	@ApiOperation("num으로 유저 정보 가져오기")
	public UserResponseDto getUserByNum(@PathVariable long	num) {
		logger.info("GET : /api/users/{num} = " + num);
		UserResponseDto user = userService.findByNum(num);

		return user;
	}
	
//
//	@PatchMapping("/user/{num}")
//	@ApiOperation("유저 내 정보 수정")
//	public String getAllUser(@RequestBody User userInfo) {
//		logger.info("PATCH : /api/users/{num} = " + userInfo.getNum());
//
//		if (userService.updateUser(userInfo) == 0) {
//			logger.error("update failed");
//			return "update failed";
//		}
//
//		User user = userService.getUserByNum(userInfo.getNum());
//
//		String jwt = jwtService.create("user", user, "user");
//
//		return jwt;
//	}

}
