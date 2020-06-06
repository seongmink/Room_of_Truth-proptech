package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Address;
import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AddressResponseDto;
import com.roomoftruth.rot.dto.FavoriteDeleteRequestDto;
import com.roomoftruth.rot.dto.FavoriteResponseDto;
import com.roomoftruth.rot.dto.FavoriteSaveRequestDto;
import com.roomoftruth.rot.repository.*;
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
	private final ContractRepository contractRepository;

	@Transactional
	public Long saveFavorite(FavoriteSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		Around around = aroundRepository.findById(requestDto.getAroundId())
				.orElseThrow(()-> new IllegalArgumentException("해당 around가 없습니다."));

//		return favoriteRepository.save(requestDto.toEntity(user, around)).getUser().getNum();
		return 0L;
	}

	@Transactional
	public Long deleteFavorite(FavoriteDeleteRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		Around around = aroundRepository.findById(requestDto.getAroundId())
				.orElseThrow(()-> new IllegalArgumentException("해당 around가 없습니다."));

		return favoriteRepository.deleteFavorite(user.getNum(), around.getAroundId());
	}


	public List<FavoriteResponseDto> getFavorite(long num) {

		List<Favorite> list = favoriteRepository.getAllByUserNum(num);

		System.out.println("list = " + list);
		System.out.println(list.size());
		List<FavoriteResponseDto> result = new ArrayList<>();

//		for(int i = 0; i < list.size(); i++) {
//			result.add(new FavoriteResponseDto(
//					contractRepository.getTop1AllByAddressOrderByContractDateDesc(list.get(i).getAround().getAddress())));
//		}

		return result;

	}

}
