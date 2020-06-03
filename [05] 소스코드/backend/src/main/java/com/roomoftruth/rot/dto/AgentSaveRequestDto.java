package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgentSaveRequestDto {

    private long userNum;
    private String name;
    private String representative;
    private String license;
    private String address;
    private String phoneNum;

    @Builder
    public AgentSaveRequestDto(long userNum, String name, String representative, String license, String address, String phoneNum) {
        this.userNum = userNum;
        this.name = name;
        this.representative = representative;
        this.license = license;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Agent toEntity(User user) {
        return Agent.builder()
                .user(user)
                .name(name)
                .representative(representative)
                .license(license)
                .address(address).build();
    }
}