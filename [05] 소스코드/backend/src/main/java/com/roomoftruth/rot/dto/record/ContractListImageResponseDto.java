package com.roomoftruth.rot.dto.record;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractListImageResponseDto {
    @Id
    @Column(name = "contract_id")
    Long contractId;
    @Column(name = "around_id")
    Long aroundId;
    String address;
    String floor;
    String ho;
    String image;

    @Builder
    public ContractListImageResponseDto(ContractListResponseDto contractListResponseDto){
        this.contractId = contractListResponseDto.getContractId();
        this.aroundId = contractListResponseDto.getAroundId();
        this.address = contractListResponseDto.getAddress();
        this.floor = contractListResponseDto.getFloor();
        this.ho = contractListResponseDto.getHo();
        this.image = "";
    }
}