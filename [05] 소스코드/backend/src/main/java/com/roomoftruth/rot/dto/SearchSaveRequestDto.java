package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchSaveRequestDto {

    private long num;
    private String keyword;

    @Builder
    public SearchSaveRequestDto(long num, String keyword) {
        this.num = num;
        this.keyword = keyword;
    }

    public Search toEntity(User user) {
        return Search.builder()
                .user(user)
                .keyword(keyword).build();
    }

}