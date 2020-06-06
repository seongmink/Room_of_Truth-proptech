package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {

    private String address;
    private String longitude;
    private String latitude;
    private String image;

    public FavoriteResponseDto(Contract contarct) {
        this.address = contarct.getAddress();
        this.longitude = contarct.getLongitude();
        this.latitude = contarct.getLatitude();
        this.image = contarct.getImage();
    }

}