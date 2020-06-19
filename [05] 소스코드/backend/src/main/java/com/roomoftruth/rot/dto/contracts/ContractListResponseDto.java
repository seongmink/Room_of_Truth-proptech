package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Getter
@Entity
public class ContractListResponseDto {
    @Id
    @Column(name = "contract_id")
    Long contractId;
    @Column(name = "around_id")
    Long aroundId;
    String address;
    String floor;
    String ho;
}