package com.roomoftruth.rot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class ContributionContractResponseDto {
    @Id
    private long contract_id;
    private String address;
    private String floor;
    private String ho;
    private String image;
    private String type;
    private String created_at;
}
