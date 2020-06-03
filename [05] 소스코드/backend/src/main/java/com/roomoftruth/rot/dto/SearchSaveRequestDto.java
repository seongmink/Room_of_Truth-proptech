package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
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

    public Search toEntity(User user) {
        return Search.builder()
                .user(user)
                .keyword(keyword).build();
    }

    @Override
    public String toString() {
        return "SearchSaveRequestDto{" +
                "userNum=" + userNum +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}