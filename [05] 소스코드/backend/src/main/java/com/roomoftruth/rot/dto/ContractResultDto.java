package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractResultDto {
    private String address;
    private String floor;
    private String ho;
    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    @Builder
    public ContractResultDto(String address, String floor, String ho, String image){
        this.address = address;
        this.floor = floor;
        this.ho = ho;
        this.image = image;
    }

}
