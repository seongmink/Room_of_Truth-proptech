package com.roomoftruth.rot.dto;

import lombok.Getter;

@Getter
public class AgentRankingResponseDto {

    private long userNum;
    private String nickname;
    private String agentName;
    private String userPicture;
    private String agentPicture;
    private int rnk;
    private int point;

}