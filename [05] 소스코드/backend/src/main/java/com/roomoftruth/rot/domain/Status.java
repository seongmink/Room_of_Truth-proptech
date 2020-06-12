//package com.roomoftruth.rot.domain;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.time.LocalDate;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name = "status")
//public class Status {
//
//    @Id
//    @Column(name = "status_id")
//    private long statusId;
//
//    @Column(nullable = false)
//    private String address;
//    private String sd;
//    private String sgg;
//    private String emd;
//
//    @Column(nullable = false)
//    private String longitude;
//    @Column(nullable = false)
//    private String latitude;
//
//    private String floor;
//    private String ho;
//    private String category;
//    private String detail;
//    private Long cost;
//
//    @Column(nullable = false)
//    private String license;
//    private String image;
//
//    private String exclusive;
//    private LocalDate startDate;
//    private LocalDate endDate;
//
//    @Builder
//    public Status(long statusId, String address, String sd, String sgg, String emd,
//                  String latitude, String longitude, String exclusive, String floor, String ho,
//                  String category, String detail, Long cost, String license, String image,
//                  LocalDate startDate, LocalDate endDate){
//        this.statusId = statusId;
//        this.address = address;
//        this.sd = sd;
//        this.sgg = sgg;
//        this.emd = emd;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.exclusive = exclusive;
//        this.floor = floor;
//        this.ho = ho;
//        this.category = category;
//        this.detail = detail;
//        this.cost = cost;
//        this.license = license;
//        this.image = image;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    @Builder
//    public Status(FabricStatusRecord fabricStatusRecord){
//        this.statusId = Long.parseLong(fabricStatusRecord.getStatus_id());
//        this.address = fabricStatusRecord.getAddress();
//        this.sd = fabricStatusRecord.getSd();
//        this.sgg = fabricStatusRecord.getSgg();
//        this.emd = fabricStatusRecord.getEmd();
//        this.latitude = fabricStatusRecord.getLatitude();
//        this.longitude = fabricStatusRecord.getLongitude();
//        this.exclusive = fabricStatusRecord.getExclusive();
//        this.floor = fabricStatusRecord.getFloor();
//        this.ho = fabricStatusRecord.getHo();
//        this.category = fabricStatusRecord.getCategory();
//        this.detail = fabricStatusRecord.getDetail();
//        this.cost = Long.parseLong(fabricStatusRecord.getCost());
//        this.license = fabricStatusRecord.getLicense();
//        this.image = ""+fabricStatusRecord.getImage();
//        String[] arr = fabricStatusRecord.getStart_date().split("-");
//        int year = Integer.parseInt(arr[0]);
//        int month = Integer.parseInt(arr[1]);
//        int day = Integer.parseInt(arr[2]);
//        System.out.println("year : " + year + ", month : " + month +", day : " + day);
//        this.startDate = LocalDate.of(year, month, day);
//        String[] arr2 = fabricStatusRecord.getEnd_date().split("-");
//        int year2 = Integer.parseInt(arr2[0]);
//        int month2 = Integer.parseInt(arr2[1]);
//        int day2 = Integer.parseInt(arr2[2]);
//        this.endDate = LocalDate.of(year2, month2, day2);
//    }
//}