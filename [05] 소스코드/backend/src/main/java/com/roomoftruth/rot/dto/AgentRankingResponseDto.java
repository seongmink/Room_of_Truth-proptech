package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
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

    public AgentRankingResponseDto(Agent entity){
        this.userNum = entity.getUser().getNum();
        this.nickname = entity.getUser().getNickname();
        this.agentName = entity.getName();
        this.userPicture = entity.getUser().getPicture();
        this.agentPicture = entity.getPicture();
        this.rnk = entity.getRnk();
        this.point = entity.getPoint();
    }

    public void updateDefaultImage() {
        this.agentPicture = "agent_default.png";
    }

}