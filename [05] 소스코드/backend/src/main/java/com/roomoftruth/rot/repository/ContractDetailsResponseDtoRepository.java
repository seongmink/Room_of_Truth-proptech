//package com.roomoftruth.rot.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ContractDetailsResponseDtoRepository extends JpaRepository<ContractDetailsResponseDto, Long> {
//
//    @Query(value = "select @RN \\:= @RN + 1 as detail_id, address, floor, ho, latitude, longitude " +
//            "from (select address, floor, ho, latitude, longitude " +
//            "from contract " +
//            "union " +
//            "select address, floor, ho, latitude, longitude " +
//            "from status)as main, (SELECT @RN \\:= 0) RR " +
//            "where latitude like ?1% AND longitude like ?2%", nativeQuery = true)
//    List<ContractDetailsResponseDto> findAllDetail(String latitude, String longitude);
//
//}
