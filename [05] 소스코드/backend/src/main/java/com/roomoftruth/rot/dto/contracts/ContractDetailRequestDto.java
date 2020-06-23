package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContractDetailRequestDto {
    private long aroundId;
    private String floor;
    private String ho;
}
