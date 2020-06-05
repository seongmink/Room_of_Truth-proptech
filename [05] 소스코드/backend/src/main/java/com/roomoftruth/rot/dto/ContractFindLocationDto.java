package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class ContractFindLocationDto {
    @Id
    private String address;
    private String latitude;
    private String longitude;

    @Builder
    public ContractFindLocationDto(String address, String latitude, String longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
