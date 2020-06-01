package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private long num;
    private String nickname;
    private String auth;
    private String phoneNum;
    private String birth;
    private String gender;
    private String picture;

    public UserResponseDto(User entity){
        this.num = entity.getNum();
        this.nickname = entity.getNickname();
        this.auth = entity.getAuth();
        this.phoneNum = entity.getPhoneNum();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();
        this.picture = entity.getPicture();
    }
}