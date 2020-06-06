package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Address;
import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AddressResponseDto;
import com.roomoftruth.rot.dto.FavoriteDeleteRequestDto;
import com.roomoftruth.rot.dto.FavoriteSaveRequestDto;
import com.roomoftruth.rot.repository.AddressRepository;
import com.roomoftruth.rot.repository.AroundRepository;
import com.roomoftruth.rot.repository.FavoriteRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final AroundRepository aroundRepository;

	@Transactional
	public Long saveFavorite(FavoriteSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		Around around = aroundRepository.findById(requestDto.getAroundId())
				.orElseThrow(()-> new IllegalArgumentException("해당 around가 없습니다."));

		return favoriteRepository.save(requestDto.toEntity(user, around)).getUser().getNum();
	}

	@Transactional
	public Long deleteFavorite(FavoriteDeleteRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		Around around = aroundRepository.findById(requestDto.getAroundId())
				.orElseThrow(()-> new IllegalArgumentException("해당 around가 없습니다."));

		return favoriteRepository.deleteFavorite(user.getNum(), around.getAroundId());
	}

}
