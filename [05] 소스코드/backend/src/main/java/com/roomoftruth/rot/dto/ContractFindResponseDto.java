package com.roomoftruth.rot.dto;

import com.roomoftruth.rot.domain.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "find")
@Entity
public class ContractFindResponseDto {
    @Id
    private String address;
    private String floor;
    private String ho;
    private String latitude;
    private String longitude;

    public ContractFindResponseDto(String address, String floor, String ho, String latitude, String longitude) {
        this.address = address;
        this.floor = floor;
        this.ho = ho;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Contract toEntity(){
        return Contract.builder()
                .address(address)
                .floor(floor)
                .ho(ho)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    @Override
    public String toString() {
        return "ContractFindResponseDto{" +
                "address='" + address + '\'' +
                ", floor='" + floor + '\'' +
                ", ho='" + ho + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
