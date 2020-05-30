package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Auth;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.dto.UserSaveRequestDto;
import com.roomoftruth.rot.jwt.IJwtService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class KakaoAPIService implements IKaKaoAPIService {

	private static final Logger logger = LoggerFactory.getLogger(KakaoAPIService.class);

	private final String requestURL = "https://kapi.kakao.com/v2/user/me";

	@Autowired
	private UserService userService;

	@Autowired
	private IJwtService IJwtService;

	public JsonNode getKaKaoUserInfo(String access_Token) {
		logger.info("KakaoAPIService - getKaKaoUserInfo");
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(requestURL);

		post.addHeader("Authorization", "Bearer " + access_Token);
		JsonNode returnNode = null;

		HttpResponse response;
		try {
			response = client.execute(post);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnNode;
	}

	public String redirectToken(JsonNode json) {
		logger.info("KakaoAPISerice : redirectToken");

		long num = json.get("id").asLong();
		String nickname = json.get("kakao_account").get("profile").get("nickname").toString();
		nickname = nickname.substring(1, nickname.length() - 1);
		String picture = null;
		if (json.get("kakao_account").get("profile").has("thumbnail_image_url")) {
			picture = json.get("kakao_account").get("profile").get("thumbnail_image_url").toString();
			picture = picture.substring(1, picture.length() - 1);
			String temp = picture.substring(0, 4);
			String temp2 = picture.substring(4, picture.length());
			picture = temp + "s" + temp2; // https 작업
		}

		UserResponseDto user = userService.findByNum(num);

		if (user == null) { // 없는 사용자면?

			User.builder()
				.num(num)
				.nickname(nickname)
				.picture(picture)
				.auth(Auth.NORMAL).build();
			logger.info("New Account : " + num);
//			userService.save(user);
		} else {
			logger.info("UPDATE enteredAt : " + num);

//			userService.updateEntered(user); // 마지막 접속 날짜를 갱신해 주고 토큰 발행
		}

//		user = userService.getUserByNum(num);

		String jwt = IJwtService.create("user", user, "user");
		logger.info("JWT : " + jwt);

		return jwt;
	}

}
