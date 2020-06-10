package com.roomoftruth.rot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String longitude;
    @Column(nullable = false)
    private String latitude;

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

    @Builder
    public Contract(Long id, String address, String latitude, String longitude, String floor, String ho){
        this.contractId = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.ho = ho;
    }

    @Builder
    public Contract(Long id, String address, String sd, String sgg, String emd,
                    String longitude, String latitude, String exclusive, String floor, String ho,
                    String kind, String detail, Long cost, String monthly,
                    String license, String image){
        this.contractId = id;
        this.address = address;
        this.sd = sd;
        this.sgg = sgg;
        this.emd = emd;
        this.longitude = longitude;
        this.latitude = latitude;
        this.exclusive = exclusive;
        this.floor = floor;
        this.ho = ho;
        this.kind = kind;
        this.detail = detail;
        this.cost = cost;
        this.monthly = monthly;
        this.license = license;
        this.image = image;
        this.contractDate = LocalDate.now();
    }

    public void setContractDate() {
        this.contractDate = LocalDate.now();
    }

    public void setSgg(String sgg) {
        this.sgg = sgg;
    }

    @Builder
    public Contract(FabricContractRecord fabricContractRecord){
        this.contractId = Long.parseLong(fabricContractRecord.getContract_id());
        this.address = fabricContractRecord.getAddress();
        this.sd = fabricContractRecord.getSd();
        this.sgg = fabricContractRecord.getSgg();
        this.emd = fabricContractRecord.getEmd();
        this.latitude = fabricContractRecord.getLatitude();
        this.longitude = fabricContractRecord.getLongitude();
        this.exclusive = fabricContractRecord.getExclusive();
        this.floor = fabricContractRecord.getFloor();
        this.ho = fabricContractRecord.getHo();
        this.kind = fabricContractRecord.getKind();
        this.detail = fabricContractRecord.getDetail();
        this.cost = Long.parseLong(fabricContractRecord.getCost());
        this.monthly = fabricContractRecord.getMonthly();
        this.license = fabricContractRecord.getLicense();
        this.image = ""+fabricContractRecord.getImage();
        this.contractDate = LocalDate.now();
    }

    public void setImage(String image){
        this.image = image;
    }

}