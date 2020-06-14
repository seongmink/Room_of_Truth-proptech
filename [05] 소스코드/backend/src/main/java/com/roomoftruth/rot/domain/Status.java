package com.roomoftruth.rot.domain;

import com.roomoftruth.rot.fabric.StatusRecord;
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

    @Column(name = "around_around_id", nullable = false)
    private Long aroundId;

    private String floor;
    private String ho;
    private String category;
    private String detail;
    private Long cost;

    @Column(nullable = false)
    private String license;
    private String image;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "is_expired")
    private String isExpired;

    public void setImage(String image) {
        this.image = image;
    }

    public Status(StatusRecord statusRecord) {
        this.statusId = Long.parseLong(statusRecord.getStatus_id());
        this.aroundId = Long.parseLong(statusRecord.getAround_around_id());
        this.floor = statusRecord.getFloor();
        this.ho = statusRecord.getHo();
        this.category = statusRecord.getCategory();
        this.detail = statusRecord.getDetail();
        this.cost = Long.parseLong(statusRecord.getCost());
        this.license = statusRecord.getLicense();
        this.image = statusRecord.getImage();
        this.startDate = LocalDate.parse(statusRecord.getStart_date());
        this.endDate = LocalDate.parse(statusRecord.getEnd_date());
        this.createdAt = LocalDate.now();
        this.isExpired = "N";
    }
}