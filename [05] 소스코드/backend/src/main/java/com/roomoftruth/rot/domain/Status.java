package com.roomoftruth.rot.domain;

import com.roomoftruth.rot.dto.record.StatusSaveRequestDto;
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

    @Builder
    public Status(long statusId, long aroundId, StatusSaveRequestDto statusSaveRequestDto) {
        this.statusId = statusId;
        this.aroundId = aroundId;
        this.floor = statusSaveRequestDto.getFloor();
        this.ho = statusSaveRequestDto.getHo();
        this.category = statusSaveRequestDto.getCategory();
        this.detail = statusSaveRequestDto.getDetail();
        this.cost = statusSaveRequestDto.getCost();
        this.license = statusSaveRequestDto.getLicense();
        this.image = statusSaveRequestDto.getImage();
        this.startDate = statusSaveRequestDto.getStartDate();
        this.endDate = statusSaveRequestDto.getEndDate();
        this.createdAt = LocalDate.now();
        this.isExpired = "N";
    }
}