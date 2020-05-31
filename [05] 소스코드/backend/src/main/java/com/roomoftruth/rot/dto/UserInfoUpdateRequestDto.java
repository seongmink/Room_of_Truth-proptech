package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateRequestDto {

    private long num;
    private String phoneNum;
    private String birth;
    private Gender gender;
    private String address;

    @Builder
    public UserInfoUpdateRequestDto(long num, String phoneNum, String birth, Gender gender, String address) {
        this.num = num;
        this.phoneNum = phoneNum;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
    }

}