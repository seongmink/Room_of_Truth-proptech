package com.roomoftruth.rot.fabric;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 패브릭 체인코드로부터 조회된 결과를 담기위한 클래스
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
public class ContractRecord {
    private String contract_id;
	private String around_around_id;
	private String exclusive;
	private String floor;
	private String ho;
	private String kind;
	private String detail;
	private String cost;
	private String monthly;
	private String license;
	private String image;
	private String contract_date;
	private String created_at;
	private String is_expired;

    public ContractRecord() {
    }
}
