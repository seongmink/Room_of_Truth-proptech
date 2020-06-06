package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Favorite;
import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {

    private long num;
    private String nickname;
    private String auth;
    private String picture;
    private List<Search> searches;
    private Interest interest;

    public UserResponseDto(User entity){
        this.num = entity.getNum();
        this.nickname = entity.getNickname();
        this.auth = entity.getAuth();
        this.picture = entity.getPicture();
        this.searches = entity.getSearches();
        this.interest = entity.getInterest();
    }
}