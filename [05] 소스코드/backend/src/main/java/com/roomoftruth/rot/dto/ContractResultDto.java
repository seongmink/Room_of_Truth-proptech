package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
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

    public ContractResultDto(String address, String floor, String ho, String image){
        this.address = address;
        this.floor = floor;
        this.ho = ho;
        this.image = image;
    }

    public Contract toEntity(){
        return Contract.builder()
                .address(address)
                .floor(floor)
                .ho(ho)
                .image(image)
                .build();
    }
}
