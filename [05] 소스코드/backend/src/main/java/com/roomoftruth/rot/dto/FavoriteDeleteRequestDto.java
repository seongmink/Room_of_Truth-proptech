package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteDeleteRequestDto {

    private long aroundId;
    private long userNum;

    @Builder
    public FavoriteDeleteRequestDto(long aroundId, long userNum) {
        this.aroundId = aroundId;
        this.userNum = userNum;
    }

    public Favorite toEntity(User user, Around around) {
        return Favorite.builder()
                .around(around).build();
    }
}