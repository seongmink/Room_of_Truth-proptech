//package com.roomoftruth.rot.repository;
//
//import com.roomoftruth.rot.domain.Status;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface StatusRepository extends JpaRepository<Status, Long> {
//    List<Status> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
//    List<Status> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
//    List<Status> findAllByLicense(String license);
//    List<Status> findTop1000AllByLicenseOrderByStartDate(String license);
//
//    @Query(value = "select image " +
//            "from status " +
//            "where address= ?1 and floor= ?2 and ho= ?3 " +
//            "order by status_id desc limit 1", nativeQuery = true)
//    String getStatusImage(String address, String floor, String ho);
//}
