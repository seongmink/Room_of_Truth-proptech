package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.InterestResponseDto;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import com.roomoftruth.rot.dto.UserFirstSaveRequestDto;
import com.roomoftruth.rot.repository.InterestRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InterestService {

	private final InterestRepository interestRepository;
	private final UserRepository userRepository;

	public InterestResponseDto findByNum(Long num) {

		User user = userRepository.findByNum(num);

		Interest interest = interestRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("관심 정보 내역이 없습니다. num = " + num));

		return new InterestResponseDto(interest);
	}

	public Long save(InterestSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());

		return interestRepository.save(requestDto.toEntity(user)).getInterestId();
	}

	public Long save(UserFirstSaveRequestDto requestDto){

		User user = userRepository.findByNum(requestDto.getNum());

		Interest interest = Interest.builder().user(user).sd(requestDto.getSd()).sgg(requestDto.getSgg())
				.first(requestDto.getFirst()).second(requestDto.getSecond()).third(requestDto.getThird())
				.gender(requestDto.getGender()).birth(requestDto.getBirth()).build();

		return interestRepository.save(interest).getUser().getNum();
	}
}
