package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ContractDetailResponseDto {
    @Id @Column(name = "contract_id")
    private long contractId;
    @Column(name = "around_id")
    private Long aroundId;
    private String address;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private Long cost;
    private String monthly;
    private String license;
    private String image;
    @Column(name = "contract_date")
    private LocalDate contractDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "is_expired")
    private String isExpired;
    @Column(name = "is_like")
    private long isLike;
}
