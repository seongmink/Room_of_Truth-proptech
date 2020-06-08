package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

    @Query(nativeQuery = true, value = "DELETE FROM favorite WHERE around = ?1 AND user = ?2")
    Long deleteFavorite(long around, long num);

    @Query(nativeQuery = true, value = "SELECT * FROM favorite WHERE user_num = :num")
    List<Favorite> getAllByUserNum(long num);

    @Query(nativeQuery = true, value = "select AVG(score) from favorite where around_around_id = ?1")
    long findByAroundId(Long aroundId);

    @Query(nativeQuery = true, value = "select * from favorite where around_around_id = ?1 AND user_num = ?2")
    Favorite findByAroundAnduserNum(long aroundId, long userNum);

    @Modifying
    @Query(nativeQuery = true, value = "update favorite set score = ?1 where around_around_id = ?2 AND user_num = ?3")
    int updateScore(int score, long aroundId, long userNum);

    @Query(nativeQuery = true, value = "select score from favorite where around_around_id = ?1 limit 1")
    String findByAroundIdInFavorite(long aroundId);
}
