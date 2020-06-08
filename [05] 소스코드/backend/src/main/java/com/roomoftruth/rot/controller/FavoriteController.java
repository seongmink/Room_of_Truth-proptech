package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.*;
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

	/**
	 *
	 * @param  address, num, isLike
	 * @return
	 */

	@PostMapping("/favorite")
	@ApiOperation("찜하기")
	public Long saveFavorite(@RequestBody SaveFavoriteDto saveFavoriteDto) {
		log.info("FavoriteController : saveFavorite");

		return favoriteService.saveFavorite(saveFavoriteDto);
	}

	@DeleteMapping("/favorite")
	@ApiOperation("찜하기 취소")
	public Long deleteFavorite(@RequestBody FavoriteDeleteRequestDto requestDto) {
		log.info("FavoriteController : deleteFavorite");

		return favoriteService.deleteFavorite(requestDto);
	}

	@GetMapping("/favorite/{num}")
	@ApiOperation("찜한 건물 가져오기")
	public List<FavoriteResponseDto> getFavorite(@PathVariable long num) {
		log.info("FavoriteController : getFavorite");

		return favoriteService.getFavorite(num);
	}

}
