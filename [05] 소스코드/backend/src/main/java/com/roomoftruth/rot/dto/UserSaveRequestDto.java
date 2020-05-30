package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Auth;
import com.roomoftruth.rot.domain.Gender;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime enteredAt;

    @Builder
    public UserSaveRequestDto(long num, String nickname, String picture) {
        this.num = num;
        this.nickname = nickname;
        this.picture = picture;
        this.createdAt = LocalDateTime.now();
        this.enteredAt = LocalDateTime.now();
    }

    public User toEntity() {
        return User.builder()
                .num(num)
                .nickname(nickname)
                .picture(picture).build();
    }

}