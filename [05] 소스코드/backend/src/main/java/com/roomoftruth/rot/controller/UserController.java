package com.roomoftruth.rot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.dto.UserFirstSaveRequestDto;
import com.roomoftruth.rot.jwt.JwtService;
import com.roomoftruth.rot.service.KakaoAPIService;
import com.roomoftruth.rot.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final KakaoAPIService kakaoAPIService;
	private final JwtService jwtService;
	private final UserService userService;

	@PostMapping("/user/kakaologin")
	@ApiOperation("카카오 로그인")
	public String kakaoLogin(@RequestParam String access_Token) {
		log.info("UserController : kakaoLogin");

		JsonNode json = kakaoAPIService.getKaKaoUserInfo(access_Token);

		long num = json.get("id").asLong();

		if(userService.findByNum(num) == null) {
			return String.valueOf(num);
		}

		String result = null;

		try {
			result = kakaoAPIService.redirectToken(json); // 토큰 발행
		} catch (Exception e) {
			log.error(e + "");
		}

		return result;
	}

	@PostMapping("/user/logintoken")
	@ApiOperation("토큰 검증")
	public Object checkToken(@RequestParam String access_token) {
		log.info("UserController : checkToken");

		Object result = null;

		if (jwtService.isUsable(access_token)) {
			result = jwtService.get("user");
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/{num}")
	@ApiOperation("num으로 유저 정보 가져오기")
	public UserResponseDto getUserByNum(@PathVariable long num) {
		log.info("UserController : getUserByNum / {}", num);
		User entity = userService.findByNum(num);

		return new UserResponseDto(entity);
	}

	@PostMapping("/user")
	@ApiOperation("새로운 유저 정보 저장하기")
	public Long saveUser(@RequestBody UserFirstSaveRequestDto requestDto) {
		log.info("UserController : saveUser / {}", requestDto.getNum());

		return userService.save(requestDto);
	}

}
