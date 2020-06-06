package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class StatusSaveRequestDto {
    @NotEmpty
    private long statusId;
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