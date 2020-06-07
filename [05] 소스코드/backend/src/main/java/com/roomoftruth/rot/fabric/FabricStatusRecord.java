package com.roomoftruth.rot.fabric;


import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.StatusSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
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
    private String exclusive; // 전용 면적
    private String floor; // 층
    private String ho; // 호
    private String category;    // 유지보수 내용
    private String detail; // 거래 내용
    private String cost; // 비용
    private String license;	// 공인중개사 번호
    private String image;	// 이미지
    private String start_date;	// 시작 날짜
    private String end_date;    // 종료 날짜

    public FabricStatusRecord() {
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public FabricStatusRecord(StatusSaveRequestDto statusSaveRequestDto){
        this.address = statusSaveRequestDto.getAddress();
        this.sd = statusSaveRequestDto.getSd();
        this.sgg = statusSaveRequestDto.getSgg();
        this.emd = statusSaveRequestDto.getEmd();
        this.longitude = statusSaveRequestDto.getLongitude();
        this.latitude = statusSaveRequestDto.getLatitude();
        this.exclusive = statusSaveRequestDto.getExclusive();
        this.floor = statusSaveRequestDto.getFloor();
        this.ho = statusSaveRequestDto.getHo();
        this.category = statusSaveRequestDto.getCategory();
        this.detail = statusSaveRequestDto.getDetail();
        this.cost = ""+statusSaveRequestDto.getCost();
        this.license = statusSaveRequestDto.getLicense();
        this.image = statusSaveRequestDto.getImage();
        this.start_date = statusSaveRequestDto.getStart_date();
        this.end_date = statusSaveRequestDto.getEnd_date();
    }

    public FabricStatusRecord(Status status){
        this.status_id = "STATUS" + status.getStatusId();
        this.address = status.getAddress();
        this.sd = status.getSd();
        this.sgg = status.getSgg();
        this.emd = status.getEmd();
        this.longitude = status.getLongitude();
        this.latitude = status.getLatitude();
        this.exclusive = status.getExclusive();
        this.floor = status.getFloor();
        this.ho = status.getHo();
        this.category = status.getCategory();
        this.detail = status.getDetail();
        this.cost = ""+status.getCost();
        this.license = status.getLicense();
        this.image = status.getImage();
        this.start_date = ""+status.getStartDate();
        this.end_date = ""+status.getEndDate();
    }

}
