package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Search;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchSaveRequestDto {

    private long userNum;
    private String keyword;

    @Builder
    public SearchSaveRequestDto(long userNum, String keyword) {
        this.userNum = userNum;
        this.keyword = keyword;
    }

    public Search toEntity() {
        return Search.builder()
                .userNum(userNum)
                .keyword(keyword).build();
    }

}