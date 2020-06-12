package com.roomoftruth.rot.domain;

import com.roomoftruth.rot.fabric.ContractRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contract")
public class Contract {

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

    public void setImage(String image) {
        this.image = image;
    }

    public Contract(ContractRecord contractRecord) {
        this.contractId = Long.parseLong(contractRecord.getContract_id());
        this.aroundId = Long.parseLong(contractRecord.getAround_around_id());
        this.exclusive = contractRecord.getExclusive();
        this.floor = contractRecord.getFloor();
        this.ho = contractRecord.getHo();
        this.kind = contractRecord.getKind();
        this.detail = contractRecord.getDetail();
        this.cost = Long.parseLong(contractRecord.getCost());
        this.monthly = contractRecord.getMonthly();
        this.license = contractRecord.getLicense();
        this.image = contractRecord.getImage();
        this.contractDate = LocalDate.parse(contractRecord.getContract_date(), DateTimeFormatter.BASIC_ISO_DATE);
        this.createdAt = LocalDate.now();
        this.isExpired = "N";
    }
}