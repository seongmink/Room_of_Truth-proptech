package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInterestUpdateRequestDto {

    private long num;
    private String first;
    private String second;
    private String third;

    @Builder
    public UserInterestUpdateRequestDto(long num, String first, String second, String third) {
        this.num = num;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "UserInterestUpdateRequestDto{" +
                "num=" + num +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                '}';
    }
}