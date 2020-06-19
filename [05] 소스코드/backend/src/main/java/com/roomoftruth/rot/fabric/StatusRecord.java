package com.roomoftruth.rot.fabric;

import com.roomoftruth.rot.dto.contracts.StatusSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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

    public StatusRecord(String PK, long aroundId, StatusSaveRequestDto statusSaveRequestDto){
        this.status_id = PK;
        this.around_around_id = String.valueOf(aroundId);
        this.floor = statusSaveRequestDto.getFloor();
        this.ho = statusSaveRequestDto.getHo();
        this.category = statusSaveRequestDto.getCategory();
        this.detail = statusSaveRequestDto.getDetail();
        this.cost = String.valueOf(statusSaveRequestDto.getCost());
        this.license = statusSaveRequestDto.getLicense();
        this.image = statusSaveRequestDto.getImage();
        this.start_date = String.valueOf(statusSaveRequestDto.getStartDate());
        this.end_date = String.valueOf(statusSaveRequestDto.getEndDate());
        this.created_at = String.valueOf(LocalDate.now());
        this.is_expired = "N";
    }
}
