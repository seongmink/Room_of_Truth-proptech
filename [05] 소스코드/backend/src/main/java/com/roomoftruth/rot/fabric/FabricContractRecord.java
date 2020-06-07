package com.roomoftruth.rot.fabric;

import com.roomoftruth.rot.domain.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 패브릭 체인코드로부터 조회된 결과를 담기위한 클래스
 */

@Getter
@Setter
@AllArgsConstructor
public class FabricContractRecord {

	private String contract_id;	// PK
	private String address;	// 주소
	private String sd;	// 시도
	private String sgg;	// 시군구
	private String emd; // 읍면동
	private String longitude; // 경도
	private String latitude; // 위도
	private String exclusive; // 전용 면적
	private String floor; // 층
	private String ho; // 호
	private String kind; // 건물 유형
	private String detail; // 거래 내용
	private String cost; // 비용
	private String monthly; // 월세
	private String license;	// 공인중개사 번호
	private String image;	// 이미지
	private String contract_date;	// 계약 날짜

	public FabricContractRecord() {
	}

	public FabricContractRecord(Contract contract){
		this.contract_id = "CONTRACT"+contract.getContractId();
		this.address = contract.getAddress();
		this.sd = contract.getSd();
		this.sgg = contract.getSgg();
		this.emd = contract.getEmd();
		this.longitude = contract.getLongitude();
		this.latitude = contract.getLatitude();
		this.exclusive = contract.getExclusive();
		this.floor = contract.getFloor();
		this.ho = contract.getHo();
		this.kind = contract.getKind();
		this.detail = contract.getDetail();
		this.cost = ""+contract.getCost();
		this.monthly = contract.getMonthly();
		this.license = contract.getLicense();
		this.image = contract.getImage();
		this.contract_date = ""+contract.getContractDate();
	}
}
