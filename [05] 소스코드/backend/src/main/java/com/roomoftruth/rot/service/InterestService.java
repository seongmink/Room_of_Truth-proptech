package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.dto.InterestResponseDto;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import com.roomoftruth.rot.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

	@Autowired
	private InterestRepository interestRepository;

	public InterestResponseDto findByNum(Long num) {
		Interest interest = interestRepository.findByNum(num)
				.orElseThrow(() -> new IllegalArgumentException("선호도 내역이 없습니다. num = " + num));

		return new InterestResponseDto(interest);
	}

	public Long save(InterestSaveRequestDto requestDto) {
		return interestRepository.save(requestDto);
	}
}
