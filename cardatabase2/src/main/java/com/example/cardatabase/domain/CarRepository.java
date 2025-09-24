package com.example.cardatabase.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    // 브랜드로 자동차 검색하는 쿼리 메서드
    List<Car> findByBrand(@Param("brand") String brand);

    // 색상으로 자동차 검색하는 쿼리 메서드
    List<Car> findByColor(@Param("color")String color);

}
