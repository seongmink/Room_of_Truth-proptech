package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

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

    public Contract toEntity(){
        return Contract.builder()
                .contractId(contractId)
                .address(address)
                .sd(sd)
                .sgg(sgg)
                .emd(emd)
                .latitude(latitude)
                .longitude(longitude)
                .exclusive(exclusive)
                .floor(floor)
                .ho(ho)
                .kind(kind)
                .detail(detail)
                .cost(cost)
                .monthly(monthly)
                .license(license)
                .image(image)
                .build();
    }
}