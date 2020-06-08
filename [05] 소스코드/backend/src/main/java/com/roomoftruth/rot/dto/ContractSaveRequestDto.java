package com.roomoftruth.rot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@ToString
public class ContractSaveRequestDto {
    @NotEmpty
    private String address;
    private String sd;
    private String sgg;
    private String emd;
    @NotEmpty
    private String latitude;
    @NotEmpty
    private String longitude;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private Long cost;
    private String monthly;
    @NotEmpty
    private String license;
    private String image;
    private String contractDate;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public void setSgg(String sgg) {
        this.sgg = sgg;
    }

    public void setEmd(String emd) {
        this.emd = emd;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

}