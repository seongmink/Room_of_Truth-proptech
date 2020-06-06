package com.roomoftruth.rot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractFindRequestDto {
    private String latitude;
    private String longitude;
}
