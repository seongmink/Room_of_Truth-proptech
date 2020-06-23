package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.AddressResponseDto;
import com.roomoftruth.rot.service.AddressService;
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
public class AddressController {

	private final AddressService addressService;

	@GetMapping("/address/{keyword}")
	@ApiOperation("주소 검색")
	public List<AddressResponseDto> getAddress(@PathVariable String keyword) {

		return addressService.getAddress(keyword);
	}
}