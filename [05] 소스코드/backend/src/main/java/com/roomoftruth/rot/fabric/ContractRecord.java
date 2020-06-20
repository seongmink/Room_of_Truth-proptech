package com.roomoftruth.rot.fabric;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.contracts.ContractSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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

    public ContractRecord(String PK, long aroundId, ContractSaveRequestDto contractSaveRequestDto){
    	this.contract_id = PK;
    	this.around_around_id = String.valueOf(aroundId);
    	this.exclusive = contractSaveRequestDto.getExclusive();
    	this.floor = contractSaveRequestDto.getFloor();
    	this.ho = contractSaveRequestDto.getHo();
    	this.kind = contractSaveRequestDto.getKind();
    	this.detail = contractSaveRequestDto.getDetail();
    	this.cost = String.valueOf(contractSaveRequestDto.getCost());
    	this.monthly = contractSaveRequestDto.getMonthly();
    	this.license = contractSaveRequestDto.getLicense();
    	this.image = contractSaveRequestDto.getImage();
    	this.contract_date = String.valueOf(contractSaveRequestDto.getContractDate());
    	this.created_at = String.valueOf(LocalDate.now());
    	this.is_expired = "N";
	}

	public ContractRecord(Contract contract){
    	this.contract_id = "CONTRACT" + contract.getContractId();
    	this.around_around_id = String.valueOf(contract.getAroundId());
    	this.exclusive = contract.getExclusive();
    	this.floor = contract.getFloor();
    	this.ho = contract.getHo();
    	this.kind = contract.getKind();
    	this.detail = contract.getDetail();
    	this.cost = String.valueOf(contract.getCost());
    	this.monthly = contract.getMonthly();
    	this.license = contract.getLicense();
    	this.image = contract.getImage();
    	this.contract_date = String.valueOf(contract.getContractDate());
    	this.created_at = String.valueOf(contract.getCreatedAt());
    	this.is_expired = contract.getIsExpired();
	}
}
