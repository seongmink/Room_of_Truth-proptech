package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusSaveRequestDto {
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

    public Status toEntity(){
        return Status.builder()
                .statusId(statusId)
                .address(address)
                .sd(sd)
                .sgg(sgg)
                .emd(emd)
                .latitude(latitude)
                .longitude(longitude)
                .exclusive(exclusive)
                .floor(floor)
                .ho(ho)
                .category(category)
                .detail(detail)
                .cost(cost)
                .license(license)
                .image(image)
                .build();
    }
}