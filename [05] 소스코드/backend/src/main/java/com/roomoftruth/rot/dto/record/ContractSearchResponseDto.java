package com.roomoftruth.rot.dto.record;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Entity
public class ContractSearchResponseDto {
    @Id
    Long aroundId;
    String latitude;
    String longitude;
}
