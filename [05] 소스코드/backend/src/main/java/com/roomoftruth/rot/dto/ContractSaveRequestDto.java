package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ContractSaveRequestDto {
    private long contractId;
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
    private String cost;
    private String monthly;
    @NotEmpty
    private String license;
    private String image;

}