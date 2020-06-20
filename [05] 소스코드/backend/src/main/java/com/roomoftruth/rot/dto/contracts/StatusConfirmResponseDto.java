package com.roomoftruth.rot.dto.contracts;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class StatusConfirmResponseDto {
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
    private String address;
}
