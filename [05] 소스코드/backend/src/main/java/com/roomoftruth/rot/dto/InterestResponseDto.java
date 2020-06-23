package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Interest;
import lombok.Getter;

@Getter
public class InterestResponseDto {

    private long userNum;
    private String first;
    private String second;
    private String third;

    public InterestResponseDto(Interest entity) {
        this.userNum = entity.getUser().getNum();
        this.first = entity.getFirst();
        this.second = entity.getSecond();
        this.third = entity.getThird();
    }
}