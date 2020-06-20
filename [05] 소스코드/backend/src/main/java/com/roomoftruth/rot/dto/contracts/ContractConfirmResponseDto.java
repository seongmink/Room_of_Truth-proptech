package com.roomoftruth.rot.dto.contracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ContractConfirmResponseDto {
    @Id
    @Column(name = "contract_id")
    private long contractId;
    @Column(name = "around_around_id", nullable = false)
    private Long aroundId;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private Long cost;
    private String monthly;
    @Column(nullable = false)
    private String license;
    private String image;
    @Column(name = "contract_date")
    private LocalDate contractDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "is_expired")
    private String isExpired;
    private String address;

}
