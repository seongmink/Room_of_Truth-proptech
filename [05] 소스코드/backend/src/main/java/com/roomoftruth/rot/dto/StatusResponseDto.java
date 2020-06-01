package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private long statusId;
    private String address;
    private String sd;
    private String sgg;
    private String emd;
    private String latitude;
    private String longitude;
    private String exclusive;
    private String floor;
    private String ho;
    private String category;
    private String detail;
    private String cost;
    private String license;
    private String image;

    public StatusResponseDto(Status entity){
        this.statusId = entity.getStatusId();
        this.address = entity.getAddress();
        this.sd = entity.getSd();
        this.sgg = entity.getSgg();
        this.emd = entity.getEmd();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.exclusive = entity.getExclusive();
        this.floor = entity.getFloor();
        this.ho = entity.getHo();
        this.category = entity.getCategory();
        this.detail = entity.getDetail();
        this.cost = entity.getCost();
        this.license = entity.getLicense();
        this.image = entity.getImage();
    }
}
