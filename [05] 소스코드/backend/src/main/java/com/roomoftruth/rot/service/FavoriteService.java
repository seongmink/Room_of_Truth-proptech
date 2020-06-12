package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.FavoriteDeleteRequestDto;
import com.roomoftruth.rot.dto.FavoriteResponseDto;
import com.roomoftruth.rot.dto.SaveFavoriteDto;
import com.roomoftruth.rot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final AroundRepository aroundRepository;
	private final ContractRepository contractRepository;

	@Transactional
	public long saveFavorite(SaveFavoriteDto saveFavoriteDto) {

		long userId = saveFavoriteDto.getUserId();
		long aroundId = aroundRepository.findAroundIdByAddress(saveFavoriteDto.getAddress());
		int score = saveFavoriteDto.getScore();

		if(favoriteRepository.findByAroundAnduserNum(aroundId, userId) != null){
			int result = favoriteRepository.updateScore(score, aroundId, userId);
		} else {
			User user = userRepository.findByNum(userId);
			Around around = aroundRepository.findByAroundId(aroundId);
			favoriteRepository.save(saveFavoriteDto.toEntity(user, around, score));
		}

		long avgScore = favoriteRepository.findByAroundId(aroundId);
		return avgScore;
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

		List<FavoriteResponseDto> result = new ArrayList<>();

//		for(int i = 0; i < list.size(); i++) {
//
//			result.add(new FavoriteResponseDto(
//					contractRepository.getTop1AllByAddressOrderByContractDateDesc(list.get(i).getAround().getAddress()), list.get(i).getFavoriteId()));
//		}

		Collections.sort(result, new Comparator<FavoriteResponseDto>() {
			@Override
			public int compare(FavoriteResponseDto o1, FavoriteResponseDto o2) {
				return (int) (o2.getFavoriteId() - o1.getFavoriteId());
			}
		});

		return result;
	}

	public long findByAroundId(Long aroundId){
		return favoriteRepository.findByAroundId(aroundId);
	}

	public String findByAroundIdInFavorite(Long aroundId){
		String result = favoriteRepository.findByAroundIdInFavorite(aroundId);
		System.out.println(result);
		return result;
	}

}
