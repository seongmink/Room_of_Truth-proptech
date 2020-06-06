package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractResponseDto {
    private long contractId;
    private String address;
    private String sd;
    private String sgg;
    private String emd;
    private String latitude;
    private String longitude;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private Long cost;
    private String monthly;
    private String license;
    private String image;

    public ContractResponseDto(Contract entity){
        this.contractId = entity.getContractId();
        this.address = entity.getAddress();
        this.sd = entity.getSd();
        this.sgg = entity.getSgg();
        this.emd = entity.getEmd();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.exclusive = entity.getExclusive();
        this.floor = entity.getFloor();
        this.ho = entity.getHo();
        this.kind = entity.getKind();
        this.detail = entity.getDetail();
        this.cost = entity.getCost();
        this.monthly = entity.getMonthly();
        this.license = entity.getLicense();
        this.image = entity.getImage();
    }
}
