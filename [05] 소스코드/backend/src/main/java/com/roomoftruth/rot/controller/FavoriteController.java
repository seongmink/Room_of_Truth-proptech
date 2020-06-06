package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.dto.AddressResponseDto;
import com.roomoftruth.rot.dto.FavoriteDeleteRequestDto;
import com.roomoftruth.rot.dto.FavoriteSaveRequestDto;
import com.roomoftruth.rot.service.AddressService;
import com.roomoftruth.rot.service.FavoriteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

	private final FavoriteService favoriteService;

	@PostMapping("/favorite")
	@ApiOperation("찜하기")
	public Long saveFavorite(@RequestBody FavoriteSaveRequestDto requestDto) {
		log.info("FavoriteController : saveFavorite");

		return favoriteService.saveFavorite(requestDto);
	}

	@DeleteMapping("/favorite")
	@ApiOperation("찜하기 취소")
	public Long deleteFavorite(@RequestBody FavoriteDeleteRequestDto requestDto) {
		log.info("FavoriteController : deleteFavorite");

		return favoriteService.deleteFavorite(requestDto);
	}

}
