package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{


    @Query(nativeQuery = true, value = "DELETE FROM favorite WHERE around = ?1 AND user = ?2")
    Long deleteFavorite(long around, long num);

    @Query(nativeQuery = true, value = "SELECT * FROM favorite WHERE user_num = :num")
    List<Favorite> getAllByUserNum(long num);
}
