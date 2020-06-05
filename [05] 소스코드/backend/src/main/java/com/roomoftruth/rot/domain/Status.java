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
@Table(name = "status")
public class Status {

    @Id
    @Column(name = "status_id")
    private long statusId;

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
    private String category;
    private String detail;
    private String cost;

    @Column(nullable = false)
    private String license;
    private String image;

    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Status(long statusId, String address, String sd, String sgg, String emd,
                  String latitude, String longitude, String exclusive, String floor, String ho,
                  String category, String detail, String cost, String license, String image,
                  LocalDate startDate, LocalDate endDate){
        this.statusId = statusId;
        this.address = address;
        this.sd = sd;
        this.sgg = sgg;
        this.emd = emd;
        this.latitude = latitude;
        this.longitude = longitude;
        this.exclusive = exclusive;
        this.floor = floor;
        this.ho = ho;
        this.category = category;
        this.detail = detail;
        this.cost = cost;
        this.license = license;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}