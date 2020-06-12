package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContributionResponseDto {

    private String address;
    private String floor;
    private String ho;
    private String image;
    private String type;
    private LocalDate date;

//    public ContributionResponseDto(Contract entity) {
//        this.address = entity.getAddress();
//        this.floor = entity.getFloor();
//        this.ho = entity.getHo();
//        this.image = entity.getImage();
//        this.type = "계약";
//        this.date = entity.getContractDate();
//    }
//
//    public ContributionResponseDto(Status entity) {
//        this.address = entity.getAddress();
//        this.floor = entity.getFloor();
//        this.ho = entity.getHo();
//        this.image = entity.getImage();
//        this.type = "상태";
//        this.date = entity.getStartDate();
//    }
}