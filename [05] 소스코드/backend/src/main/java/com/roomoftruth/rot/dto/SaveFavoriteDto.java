package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveFavoriteDto {
    private String address;
    private long userId;
    private int score;

    @Builder
    public SaveFavoriteDto(String address, long userId, int score) {
        this.address = address;
        this.userId = userId;
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
