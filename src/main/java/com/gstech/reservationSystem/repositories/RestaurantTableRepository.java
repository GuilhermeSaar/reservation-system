package com.gstech.reservationSystem.repositories;

import com.gstech.reservationSystem.orm.RestaurantTable;
import com.gstech.reservationSystem.orm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    @Query(value = "SELECT *FROM tb_restaurant_table " +
            "WHERE status = 'AVAILABLE'", nativeQuery = true)
    List<RestaurantTable> allAvailableTables();

    Optional<RestaurantTable> findByName(String name);
    Optional<RestaurantTable> findById(Long id);
}
