package com.rot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.rot.api.KakaoAPI;
import com.rot.jwt.JwtService;
import com.rot.model.User;
import com.rot.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private KakaoAPI kakao;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private IUserService userService;

	@PostMapping("/kakaologin")
	@ApiOperation("카카오 로그인")
	public String login(@RequestParam String access_Token) {
		logger.info("POST : /api/kakaologin");

		JsonNode json = kakao.getKaKaoUserInfo(access_Token);

		String result = null;

		try {
			result = kakao.redirectToken(json); // 토큰 발행
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

		if (jwtService.isUsable(access_token)) {
			result = jwtService.get("user");
		}
		logger.info(result.toString());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//	@GetMapping("/users")
//	@ApiOperation("모든 유저 가져오기")
//	public List<User> getAllUser() {
//		logger.info("GET : /api/users");
//		List<User> userList = userService.getAllUser();
//
//		if (userList == null || userList.isEmpty()) {
//			logger.error("NO DATA");
//			return null;
////        	throw new EmptyListException("NO DATA");
//		}
//
//		return userList;
//	}

//	@GetMapping("/users/{num}")
//	@ApiOperation("num으로 유저 정보 가져오기")
//	public User getUserByNum(@PathVariable long num) {
//		logger.info("GET : /api/users/{num} = " + num);
//		User user = userService.getUserByNum(num);
//
//		if (user == null) {
//			logger.error("NOT FOUND NUM: ", num);
//			return null;
////	          throw new NotFoundException(num + " 회원 정보를 찾을 수 없습니다.");
//		}
//
//		return user;
//	}
//
//	@PatchMapping("/users/{num}")
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
