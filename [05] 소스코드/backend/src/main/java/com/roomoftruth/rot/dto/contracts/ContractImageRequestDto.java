package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class ContractImageRequestDto {
    @Id
    @Column(name = "contract_id")
    String contractId;
    String image;
}
