package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.DTO.ReservationDTO;
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

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantTableRepository tableRepository;

    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll(String userEmail) {

        var user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + userEmail));

        List<Reservation> reservations = reservationRepository.findAllByUserId(user.getId());

        return reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getReservationDate(),
                        reservation.getReservationStatus()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createReservation(CreateReservationDTO data, String userEmail) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        var table = tableRepository.findById(data.tableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        if(!table.getStatus().equals(TableStatus.AVAILABLE)) {
            throw new TableNotAvailableException("Table is not available");
        }

        //valida horario
        LocalTime reservationTime = data.dateTime().toLocalTime();
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(22, 0);

        if(reservationTime.isBefore(openingTime) || reservationTime.isAfter(closingTime)) {
            throw new RuntimeException("Reservations are only allowed between 10:00 and 22:00");
        }

        if (data.guestCount() > table.getCapacity()) {
            throw new RuntimeException("Number of guests exceeds table capacity");
        }

        var reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTable(table);
        reservation.setReservationDate(data.dateTime());
        reservation.setReservationStatus(ReservationStatus.ACTIVE);

        table.setStatus(TableStatus.RESERVED);
        tableRepository.save(table);

        reservationRepository.save(reservation);
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

        var table = reservation.getTable();
        table.setStatus(TableStatus.AVAILABLE);
        tableRepository.save(table);
    }
}
