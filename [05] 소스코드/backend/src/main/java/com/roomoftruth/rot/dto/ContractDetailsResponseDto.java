package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ContractDetailsResponseDto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;
    private String address;
    private String floor;
    private String ho;
    private String latitude;
    private String longitude;
    private String image;

    @Builder
    public ContractDetailsResponseDto(Long detailId, String address, String floor, String ho, String latitude, String longitude, String image){
        this.detailId = detailId;
        this.address = address;
        this.floor = floor;
        this.ho = ho;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }
}