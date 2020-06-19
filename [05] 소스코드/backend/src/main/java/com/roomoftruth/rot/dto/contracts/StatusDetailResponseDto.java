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
public class StatusDetailResponseDto {
    @Id @Column(name = "status_id")
    private long statusId;
    @Column(name = "around_id")
    private Long aroundId;
    private String address;
    private String floor;
    private String ho;
    private String category;
    private String detail;
    private Long cost;
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
    @Column(name = "is_like")
    private long isLike;
}
