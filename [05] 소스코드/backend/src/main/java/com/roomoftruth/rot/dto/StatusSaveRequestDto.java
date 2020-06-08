package com.roomoftruth.rot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@Setter
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

}