package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.InterestResponseDto;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import com.roomoftruth.rot.repository.InterestRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InterestService {

	private final InterestRepository interestRepository;
	private final UserRepository userRepository;

	public InterestResponseDto findByNum(Long num) {

		User user = userRepository.findByNum(num);

		Interest interest = interestRepository.findByUser(user);

		return new InterestResponseDto(interest);
	}

	@Transactional
	public Long save(InterestSaveRequestDto requestDto) {

		if(requestDto.getSd().equals("세종특별자치시"))
			requestDto.changeSgg();

		User user = userRepository.findByNum(requestDto.getUserNum());
		Interest interest = interestRepository.findByUser(user);

		interest.update(requestDto);

		return requestDto.getUserNum();
	}
}
