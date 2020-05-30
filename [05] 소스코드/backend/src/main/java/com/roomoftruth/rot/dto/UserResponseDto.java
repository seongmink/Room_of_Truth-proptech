package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Auth;
import com.roomoftruth.rot.domain.Gender;
import com.roomoftruth.rot.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private long id;
    private long num;
    private String nickname;
    private Auth auth;
    private String phoneNum;
    private String birth;
    private Gender gender;
    private String address;
    private String picture;

    public UserResponseDto(User entity){
        this.id = entity.getId();
        this.num = entity.getNum();
        this.nickname = entity.getNickname();
        this.auth = entity.getAuth();
        this.phoneNum = entity.getPhoneNum();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();
        this.address = entity.getAddress();
        this.picture = entity.getPicture();
    }
}