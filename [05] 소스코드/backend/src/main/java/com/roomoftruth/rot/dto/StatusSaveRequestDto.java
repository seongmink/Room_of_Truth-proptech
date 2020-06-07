package com.roomoftruth.rot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@NoArgsConstructor
public class StatusSaveRequestDto {
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
    private String category;
    private String detail;
    private Long cost;
    @NotEmpty
    private String license;
    private String image;
    private String start_date;
    private String end_date;

    public void setSd(String sd) {
        this.sd = sd;
    }

    public void setSgg(String sgg) {
        this.sgg = sgg;
    }

    public void setEmd(String emd) {
        this.emd = emd;
    }

}