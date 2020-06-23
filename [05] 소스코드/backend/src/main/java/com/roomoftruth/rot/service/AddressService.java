package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Address;
import com.roomoftruth.rot.dto.AddressResponseDto;
import com.roomoftruth.rot.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {

	private final AddressRepository addressRepository;

	public List<AddressResponseDto> getAddress(String keyword) {

		List<Address> address = addressRepository.findSimilarAddress(keyword);
		List<AddressResponseDto> result = new ArrayList<>();

		for (int i = 0; i < address.size(); i++) {
			result.add(new AddressResponseDto(address.get(i)));
		}

		return result;
	}

}
