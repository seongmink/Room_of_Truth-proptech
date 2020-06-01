package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRespository extends JpaRepository<Status, Long> {
    List<Status> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
    List<Status> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
    List<Status> findAllByLicense(String license);
}
