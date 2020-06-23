package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Search;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SearchResponseDto {

    private long searchId;
    private long userNum;
    private String keyword;
    private LocalDateTime updatedAt;

    public SearchResponseDto(Search entity) {
        this.searchId = entity.getSearchId();
        this.userNum = entity.getUser().getNum();
        this.keyword = entity.getKeyword();
        this.updatedAt = entity.getUpdatedAt();
    }

}