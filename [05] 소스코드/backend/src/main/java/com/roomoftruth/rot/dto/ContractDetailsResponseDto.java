package com.roomoftruth.rot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class ContractDetailsResponseDto {
    @Id
    @Column(name = "contract_id")
    private Long contractId;
    private String address;
    private String latitude;
    private String longitude;
    private String floor;
    private String ho;
    private String image;
    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Builder
    public ContractDetailsResponseDto(Long contractId, String address, String latitude, String longitude, String floor,
                                      String ho, String image, LocalDate contract_date){
        this.contractId = contractId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.ho = ho;
        this.image = image;
        this.contractDate = contract_date
        ;
    }
}