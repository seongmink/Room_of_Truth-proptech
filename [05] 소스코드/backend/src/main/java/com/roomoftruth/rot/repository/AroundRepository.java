package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Around;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AroundRepository extends JpaRepository<Around, Long> {

    List<Around> findByAddressContaining(String key);

    List<String> findAddressByAroundId(long id);
}
