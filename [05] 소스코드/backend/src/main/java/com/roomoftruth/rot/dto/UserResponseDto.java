package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private long num;
    private String nickname;
    private String auth;
    private String picture;

    public UserResponseDto(User entity){
        this.num = entity.getNum();
        this.nickname = entity.getNickname();
        this.auth = entity.getAuth();
        this.picture = entity.getPicture();
    }
}