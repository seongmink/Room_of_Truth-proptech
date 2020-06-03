package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatusFindRequestDto {
    private String address;
    private String floor;
    private String ho;

    public Contract toEntity(){
        return Contract.builder()
                .address(address)
                .floor(floor)
                .ho(ho)
                .build();
    }
}
