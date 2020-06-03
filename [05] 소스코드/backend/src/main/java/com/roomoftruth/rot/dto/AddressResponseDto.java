package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Address;
import lombok.Getter;

@Getter
public class AddressResponseDto {

    private long addressId;
    private String roadAddress;

    public AddressResponseDto(Address entity) {
        this.addressId = entity.getAddressId();
        this.roadAddress = entity.getRoadAddress();
    }
}