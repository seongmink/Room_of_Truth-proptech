package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractListRequestDto {
    String longitude;
    String latitude;
    String sd;
    String sgg;
}
