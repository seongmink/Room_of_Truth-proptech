package com.roomoftruth.rot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    public String toString() {
        return "FabricResponseDto{" +
                "contractId='" + contractId + '\'' +
                ", address='" + address + '\'' +
                ", sd='" + sd + '\'' +
                ", sgg='" + sgg + '\'' +
                ", emd='" + emd + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", exclusive='" + exclusive + '\'' +
                ", floor='" + floor + '\'' +
                ", ho='" + ho + '\'' +
                ", kind='" + kind + '\'' +
                ", detail='" + detail + '\'' +
                ", cost='" + cost + '\'' +
                ", monthly='" + monthly + '\'' +
                ", license='" + license + '\'' +
                ", image='" + image + '\'' +
                ", contractDate='" + contractDate + '\'' +
                ", category='" + category + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
