package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private long num;
    private String sd;
    private String sgg;
    private String first;
    private String second;
    private String third;
    private String birth;
    private String gender;

    @Builder
    public UserSaveRequestDto(long num, String sd, String sgg, String first, String second, String third, String birth, String gender) {
        this.num = num;
        this.sd = sd;
        this.sgg = sgg;
        this.first = first;
        this.second = second;
        this.third = third;
        this.birth = birth;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserSaveRequestDto{" +
                "num=" + num +
                ", sd='" + sd + '\'' +
                ", sgg='" + sgg + '\'' +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                ", birth='" + birth + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}