package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.fabric.FabricContractRecord;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FabricResponseDto {
    private String contractId;
    private String address;
    private String sd;
    private String sgg;
    private String emd;
    private String longitude;
    private String latitude;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private String cost;
    private String monthly;
    private String license;
    private String image;
    private String contractDate;
    private String category;
    private String startDate;
    private String endDate;
    private String type;
    private String isLike;

}
