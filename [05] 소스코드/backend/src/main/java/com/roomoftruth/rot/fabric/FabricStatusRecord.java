package com.roomoftruth.rot.fabric;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FabricStatusRecord {
    private String status_id;	// PK
    private String address;	// 주소
    private String sd;	// 시도
    private String sgg;	// 시군구
    private String emd; // 읍면동
    private String longitude; // 경도
    private String latitude; // 위도
    private String floor; // 층
    private String ho; // 호
    private String category;    // 유지보수 내용
    private String detail; // 거래 내용
    private String cost; // 비용
    private String license;	// 공인중개사 번호
    private String image;	// 이미지
    private String exclusive; // 전용 면적
    private String start_date;	// 시작 날짜
    private String end_date;    // 종료 날짜

    public FabricStatusRecord() {
    }
}
