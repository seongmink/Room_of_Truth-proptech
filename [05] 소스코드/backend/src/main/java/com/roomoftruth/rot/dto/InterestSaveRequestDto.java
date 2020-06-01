package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestSaveRequestDto {

    private long num;
    private String sd;
    private String sgg;
    private String first;
    private String second;
    private String third;
    private String birth;
    private String gender;

    @Builder
    public InterestSaveRequestDto(long num, String sd, String sgg, String first, String second, String third, String gender, String birth) {
        this.num = num;
        this.sd = sd;
        this.sgg = sgg;
        this.first = first;
        this.second = second;
        this.third = third;
        this.birth = birth;
        this.gender = gender;
    }

    public Interest toEntity(User user) {
        return Interest.builder()
                .user(user)
                .sd(sd)
                .sgg(sgg)
                .first(first)
                .second(second)
                .third(third)
                .birth(birth)
                .gender(gender).build();
    }
}