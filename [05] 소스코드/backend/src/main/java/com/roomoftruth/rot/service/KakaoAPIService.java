package com.roomoftruth.rot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.jwt.JwtService;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class KakaoAPIService {

	private static final Logger logger = LoggerFactory.getLogger(KakaoAPIService.class);

	private final String requestURL = "https://kapi.kakao.com/v2/user/me";

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	public JsonNode getKaKaoUserInfo(String access_Token) {
		logger.info("KakaoAPIService : getKaKaoUserInfo");
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

	@Transactional
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

		User user = userService.findByNum(num);
		user.update(nickname, picture);

		UserResponseDto userResponseDto = new UserResponseDto(user);

		String jwt = jwtService.create("user", userResponseDto, "user");

		return jwt;
	}

}
