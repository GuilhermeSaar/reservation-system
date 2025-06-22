package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.DTO.UpdateTableDTO;
import com.gstech.reservationSystem.enums.TableStatus;
import com.gstech.reservationSystem.enums.UserRole;
import com.gstech.reservationSystem.exceptions.*;
import com.gstech.reservationSystem.orm.RestaurantTable;
import com.gstech.reservationSystem.repositories.TableRepository;
import com.gstech.reservationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<RestaurantTableDTO> findAllTables() {

        List<RestaurantTable> listTables = tableRepository.findAllTablesNotInactive();

        return listTables.stream()
                .map(table -> new RestaurantTableDTO(
                        table.getId(),
                        table.getName(),
                        table.getCapacity(),
                        table.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createNewTable(RestaurantTableDTO data, String userEmail) {

        var user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado")
        );

        if(tableRepository.findByName(data.name()).isPresent()) {
            throw new ResourceAlreadyExistsException("Mesa já existente");
        }

        if(!user.getRole().equals(UserRole.ADMIN)) {
            throw new UserNotAllowedException("Usuário sem permissão");
        }

        var table = new RestaurantTable();
        table.setName(data.name());
        table.setCapacity(data.capacity());
        table.setStatus(TableStatus.AVAILABLE);

        tableRepository.save(table);
    }

    @Transactional
    public void deleteTable(Long id, String userEmail) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var table = tableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Mesa não encontrada")
        );

        if(!table.getStatus().equals(TableStatus.INACTIVE)) {
            throw new ActionNotAllowedException("Apenas mesas inativas podem ser excluidas");
        }

        if(!user.getRole().equals(UserRole.ADMIN)) {
            throw new UserNotAllowedException("Usuário sem permissão");
        }

        tableRepository.delete(table);
    }

    @Transactional
    public void updateTable(Long id, String userEmail, UpdateTableDTO data) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if(!user.getRole().equals(UserRole.ADMIN)) {
            throw new UserNotAllowedException("Usuário sem permissão");
        }

        var table = tableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Mesa não encontrada")
        );

        if (table.getStatus().equals(TableStatus.RESERVED)) {
            throw new ActionNotAllowedException("Mesa com estatus reservado, somente mesas disponíveis podem ser atualizadas");
        }

        table.setName(data.name());
        table.setCapacity(data.capacity());
        table.setStatus(data.status());

        tableRepository.save(table);
    }
}
