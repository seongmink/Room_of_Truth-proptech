package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {

    private long favoriteId;
    private String address;
    private String longitude;
    private String latitude;
    private String image;


//    public FavoriteResponseDto(Contract contract, long favoriteId) {
//        this.favoriteId = favoriteId;
//        this.address = contract.getAddress();
//        this.longitude = contract.getLongitude();
//        this.latitude = contract.getLatitude();
//        this.image = contract.getImage();
//    }

}