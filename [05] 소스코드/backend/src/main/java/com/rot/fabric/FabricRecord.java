package com.rot.fabric;

/**
 * 패브릭 체인코드로부터 조회된 결과를 담기위한 클래스
 */

public class FabricRecord {

	private String num;
	private String address;
	private String dong = "0";
	private String ho = "0";
	private String latitude; // 위도
	private String longitude; // 경도
	private String supply; // 공급 면적
	private String exclusive; // 전용 면적
	private String details; // 계약 내용
	private String cost; // 계약 비용
	private String startDate; // 시작 날짜
	private String endDate = "0"; // 종료 날짜
	private String name; // 임대인
	private String license; // 공인중개사 번호
	private String image;	// 건물 사진 파일명
	private String createdAt;
	private String expiredAt;

	public FabricRecord() {
	}

	public FabricRecord(String num, String address, String dong, String ho, String latitude, String longitude, String supply, String exclusive, String details, String cost, String startDate, String endDate, String name, String license, String image, String createdAt, String expiredAt) {
		this.num = num;
		this.address = address;
		this.dong = dong;
		this.ho = ho;
		this.latitude = latitude;
		this.longitude = longitude;
		this.supply = supply;
		this.exclusive = exclusive;
		this.details = details;
		this.cost = cost;
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.license = license;
		this.image = image;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
	}

	@Override
	public String toString() {
		return "FabricRecord{" +
				"num='" + num + '\'' +
				", address='" + address + '\'' +
				", dong='" + dong + '\'' +
				", ho='" + ho + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				", supply='" + supply + '\'' +
				", exclusive='" + exclusive + '\'' +
				", details='" + details + '\'' +
				", cost='" + cost + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", name='" + name + '\'' +
				", license='" + license + '\'' +
				", image='" + image + '\'' +
				", createdAt='" + createdAt + '\'' +
				", expiredAt='" + expiredAt + '\'' +
				'}';
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	public String getExclusive() {
		return exclusive;
	}

	public void setExclusive(String exclusive) {
		this.exclusive = exclusive;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}
}
