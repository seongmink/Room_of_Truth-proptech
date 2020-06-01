package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Interest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestSaveRequestDto {

    private long userNum;
    private String first;
    private String second;
    private String third;

    @Builder
    public InterestSaveRequestDto(long userNum, String first, String second, String third) {
        this.userNum = userNum;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Interest toEntity() {
        return Interest.builder()
                .userNum(userNum)
                .first(first)
                .second(second)
                .third(third).build();
    }
}