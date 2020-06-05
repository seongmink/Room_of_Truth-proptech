package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.User;
import lombok.Getter;

@Getter
public class AgentDetailResponseDto {

    private Long agentId;
    private String name;
    private String representative;
    private String license;
    private String address;
    private String picture;
    private int count;
    private int point;
    private int rnk;
    private User user;

    public AgentDetailResponseDto(Agent entity) {
        this.agentId = entity.getAgentId();
        this.name = entity.getName();
        this.representative = entity.getRepresentative();
        this.license = entity.getLicense();
        this.address = entity.getAddress();
        this.picture = entity.getPicture();
        this.count = entity.getCount();
        this.point = entity.getPoint();
        this.rnk = entity.getRnk();
        this.user = entity.getUser();
    }
}