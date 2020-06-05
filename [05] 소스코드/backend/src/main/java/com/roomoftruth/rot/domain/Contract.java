package com.roomoftruth.rot.domain;

import com.roomoftruth.rot.dto.ContractSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "contract")
public class Contract {

    @Id @Column(name = "contract_id")
    private long contractId;

    @Column(nullable = false)
    private String address;
    private String sd;
    private String sgg;
    private String emd;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;
    private String exclusive;
    private String floor;
    private String ho;
    private String kind;
    private String detail;
    private String cost;
    private String monthly;

    @Column(nullable = false)
    private String license;
    private String image;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Builder
    public Contract(Long id, String address, String latitude, String longitude, String floor, String ho){
        this.contractId = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.ho = ho;
    }

    /**
     * ContractController    @PostMapping("/buildings")
     * ContractService
     */

    public void setContractDate() {
        this.contractDate = LocalDate.now();
    }

    @Builder
    public Contract(ContractSaveRequestDto contractSaveRequestDto){
        this.contractId = contractSaveRequestDto.getContractId();
        this.address = contractSaveRequestDto.getAddress();
        this.sd = contractSaveRequestDto.getSd();
        this.sgg = contractSaveRequestDto.getSgg();
        this.emd = contractSaveRequestDto.getEmd();
        this.latitude = contractSaveRequestDto.getLatitude();
        this.longitude = contractSaveRequestDto.getLongitude();
        this.exclusive = contractSaveRequestDto.getExclusive();
        this.floor = contractSaveRequestDto.getFloor();
        this.ho = contractSaveRequestDto.getHo();
        this.kind = contractSaveRequestDto.getKind();
        this.detail = contractSaveRequestDto.getDetail();
        this.cost = contractSaveRequestDto.getCost();
        this.monthly = contractSaveRequestDto.getMonthly();
        this.license = contractSaveRequestDto.getLicense();
        this.image = contractSaveRequestDto.getImage();
        this.contractDate = LocalDate.now();
    }

    public void setImage(String image){
        this.image = image;
    }

}