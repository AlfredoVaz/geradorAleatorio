package com.geradorAleatorio.repository;

import com.geradorAleatorio.model.RandomActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RandomActivityJpaRepository extends JpaRepository<RandomActivity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM ACTIVITY WHERE ACTIVITY_KEY = :key", nativeQuery = true)
    boolean existsByKey(@Param("key") String key);
}


