package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteSaveRequestDto {

    private long aroundId;
    private long userNum;
    private int score;

    @Builder
    public FavoriteSaveRequestDto(long aroundId, long userNum, int score) {
        this.aroundId = aroundId;
        this.userNum = userNum;
        this.score = score;
    }

    public Favorite toEntity(User user, Around around) {
        return Favorite.builder()
                .user(user)
                .around(around)
                .score(score).build();
    }

    public Favorite toEntity(User user, Around around, int score) {
        return Favorite.builder()
                .user(user)
                .around(around)
                .score(score)
                .build();
    }
}