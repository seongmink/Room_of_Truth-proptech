package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Auth;
import com.roomoftruth.rot.domain.Gender;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private long num;
    private String nickname;
    private Auth auth;
    private String phoneNum;
    private String birthYear;
    private Gender gender;
    private String address;
    private String picture;

    @Builder
    public UserSaveRequestDto(long num, String nickname, String picture, Auth auth) {
        this.num = num;
        this.nickname = nickname;
        this.picture = picture;
        this.auth = auth;
    }

    public User toEntity() {
        return User.builder()
                .num(num)
                .nickname(nickname)
                .picture(picture)
                .auth(Auth.GENERAL).build();
    }

}