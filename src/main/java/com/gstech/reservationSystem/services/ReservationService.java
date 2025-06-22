package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.CreateReservationDTO;
import com.gstech.reservationSystem.DTO.ReservationDTO;
import com.gstech.reservationSystem.enums.ReservationStatus;
import com.gstech.reservationSystem.enums.TableStatus;
import com.gstech.reservationSystem.enums.UserRole;
import com.gstech.reservationSystem.exceptions.*;
import com.gstech.reservationSystem.orm.Reservation;
import com.gstech.reservationSystem.repositories.ReservationRepository;
import com.gstech.reservationSystem.repositories.TableRepository;
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
    private TableRepository tableRepository;

    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll(String userEmail) {

        var user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado"));

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
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var table = tableRepository.findById(data.tableId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada"));

        if(!table.getStatus().equals(TableStatus.AVAILABLE)) {
            throw new TableNotAvailableException("Mesa não disponível");
        }

        //valida horario
        LocalTime reservationTime = data.dateTime().toLocalTime();
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(22, 0);

        if(reservationTime.isBefore(openingTime) || reservationTime.isAfter(closingTime)) {
            throw new ActionNotAllowedException("Reservas só são permitidas entre as 10:00 e as 22:00.");
        }

        if (data.guestCount() > table.getCapacity()) {
            throw new TableNotAvailableException("Mesa indisponível ou capacidade excedida");
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
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ResourceNotFoundException("Reserva não encontrada"));

        if(!Objects.equals(user.getId(), reservation.getUser().getId()) && user.getRole() != UserRole.ADMIN) {
            throw new UserNotAllowedException(
                    "Usuário não está autorizado a desfazer essa reserva");
        }

        if (reservation.getReservationStatus().equals(ReservationStatus.CANCELED)) {
            throw new ActionNotAllowedException("A reserva já foi cancelada");
        }
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);

        var table = reservation.getTable();
        table.setStatus(TableStatus.AVAILABLE);
        tableRepository.save(table);
    }
}
