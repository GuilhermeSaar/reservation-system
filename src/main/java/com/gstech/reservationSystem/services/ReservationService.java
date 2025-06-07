package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.enums.ReservationStatus;
import com.gstech.reservationSystem.enums.TableStatus;
import com.gstech.reservationSystem.exceptions.TableNotAvailableException;
import com.gstech.reservationSystem.orm.Reservation;
import com.gstech.reservationSystem.repositories.ReservationRepository;
import com.gstech.reservationSystem.repositories.RestaurantTableRepository;
import com.gstech.reservationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantTableRepository tableRepository;


    @Transactional
    public void createReservation(CreateReservationDTO data, String userEmail) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        var table = tableRepository.findById(data.tableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        if(!table.getStatus().equals(TableStatus.AVAILABLE)) {
            throw new TableNotAvailableException("Table is not available");
        }

        var reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setReservationDate(data.dateTime());
        reservation.setReservationStatus(ReservationStatus.ACTIVE);

        reservationRepository.save(reservation);

        table.setStatus(TableStatus.RESERVED);
        tableRepository.save(table);
    }

    @Transactional
    public void cancelReservation(Long reservationId, String userEmail) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        var reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new RuntimeException("Reservation not found"));

        if(!Objects.equals(user.getId(), reservation.getUser().getId())) {
            throw new UsernameNotFoundException(
                    "user is not authorized to cancel the reservation");
        }

        if (reservation.getReservationStatus().equals(ReservationStatus.CANCELED)) {
            throw new RuntimeException("Reservation has already been canceled");
        }
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }
}
