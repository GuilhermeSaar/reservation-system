package com.gstech.reservationSystem.repositories;

import com.gstech.reservationSystem.orm.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT *FROM tb_reservation " +
            "WHERE user_id = :userId", nativeQuery = true)
    List<Reservation> findAllByUserId(@Param("userId") Long userId);
}
