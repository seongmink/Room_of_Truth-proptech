package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private long num;
    private String nickname;
    private String auth;
    private String picture;

    @Builder
    public UserSaveRequestDto(long num, String nickname, String picture, String auth) {
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
                .auth(auth).build();
    }

}