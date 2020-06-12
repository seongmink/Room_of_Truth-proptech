package com.roomoftruth.rot.fabric;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 패브릭 체인코드로부터 조회된 결과를 담기위한 클래스
 */

@ToString
@Setter
@Getter
@AllArgsConstructor
public class StatusRecord {
    private String status_id;
    private String around_around_id;
    private String floor;
    private String ho;
    private String category;
    private String detail;
    private String cost;
    private String license;
    private String image;
    private String start_date;
    private String end_date;
    private String created_at;
    private String is_expired;

    public StatusRecord(){}
}
