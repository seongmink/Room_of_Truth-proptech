package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRespository extends JpaRepository<Contract, Long> {
    List<Contract> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
    List<Contract> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
    List<Contract> findAllByLicense(String license);
}
