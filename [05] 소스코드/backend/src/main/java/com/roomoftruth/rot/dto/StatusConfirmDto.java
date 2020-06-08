package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.fabric.FabricStatusRecord;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusConfirmDto {
    private String statusId;
    private String address;
    private String sd;
    private String sgg;
    private String emd;
    private String longitude;
    private String latitude;
    private String floor;
    private String ho;
    private String category;
    private String detail;
    private String cost;
    private String license;
    private String image;
    private String exclusive;
    private String startDate;
    private String endDate;

    public StatusConfirmDto(FabricStatusRecord fabricStatusRecord){
        this.statusId = fabricStatusRecord.getStatus_id();
        this.address = fabricStatusRecord.getAddress();
        this.sd = fabricStatusRecord.getSd();
        this.sgg = fabricStatusRecord.getSgg();
        this.emd = fabricStatusRecord.getEmd();
        this.longitude = fabricStatusRecord.getLongitude();
        this.latitude = fabricStatusRecord.getLatitude();
        this.floor = fabricStatusRecord.getFloor();
        this.ho = fabricStatusRecord.getHo();
        this.category = fabricStatusRecord.getCategory();
        this.detail = fabricStatusRecord.getDetail();
        this.cost = fabricStatusRecord.getCost();
        this.license = fabricStatusRecord.getLicense();
        this.image = fabricStatusRecord.getImage();
        this.exclusive = fabricStatusRecord.getExclusive();
        this.startDate = fabricStatusRecord.getStart_date();
        this.endDate = fabricStatusRecord.getEnd_date();
    }
}
