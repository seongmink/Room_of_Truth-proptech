//package com.roomoftruth.rot.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ContractFindResponseRepository extends JpaRepository<ContractFindResponseDto, String> {
//    @Query(value = "select distinct address, floor, ho, latitude, longitude from" +
//            "( select distinct address, floor, ho, latitude, longitude from contract union select distinct address, floor, ho, latitude, longitude from status) " +
//            "as main  where latitude= ?1 and longitude= ?2 order by floor asc, ho asc", nativeQuery = true)
//    List<ContractFindResponseDto> findAllDetails(String latitude, String longitude);
//
//}
