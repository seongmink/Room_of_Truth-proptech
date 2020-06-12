package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Address;
import com.roomoftruth.rot.domain.Around;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AroundRepository extends JpaRepository<Around, Long> {

    List<Around> findByAddressContaining(String key);

    List<String> findAddressByAroundId(long id);

    @Query(value = "select around_id from around where address = ?1", nativeQuery = true)
    Long findByAddress(String addr);

    @Query(value = "select * from around where around_id = ?1", nativeQuery = true)
    Around findByAroundId(Long aroundId);

    @Query(value = "select around_id from around where address = ?1", nativeQuery = true)
    Long findAroundIdByAddress(String address);

    Around findTop1ByAddress(String address);
}
