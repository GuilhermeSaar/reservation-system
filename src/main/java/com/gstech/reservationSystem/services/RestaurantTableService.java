package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.RestaurantTableDTO;
import com.gstech.reservationSystem.enums.TableStatus;
import com.gstech.reservationSystem.enums.UserRole;
import com.gstech.reservationSystem.exceptions.TableNameAlreadyExistsException;
import com.gstech.reservationSystem.exceptions.TableNotFoundException;
import com.gstech.reservationSystem.orm.RestaurantTable;
import com.gstech.reservationSystem.repositories.RestaurantTableRepository;
import com.gstech.reservationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<RestaurantTableDTO> findAllTablesAvailable() {

        List<RestaurantTable> listAvailable = restaurantTableRepository.allAvailableTables();

        return listAvailable.stream()
                .map(table -> new RestaurantTableDTO(
                        table.getId(),
                        table.getName(),
                        table.getCapacity()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createNewTable(RestaurantTableDTO data) {

        if(restaurantTableRepository.findByName(data.name()).isPresent()) {

            throw new TableNameAlreadyExistsException("The table name is already taken.");
        }

        var table = new RestaurantTable();
        table.setName(data.name());
        table.setCapacity(data.capacity());
        table.setStatus(TableStatus.AVAILABLE);

        restaurantTableRepository.save(table);
    }

    @Transactional
    public void deleteTable(Long id, String userEmail) {

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        var table = restaurantTableRepository.findById(id).orElseThrow(
                () -> new TableNotFoundException("Table Not Found with id: " + id)
        );

        if (!table.getStatus().equals(TableStatus.AVAILABLE)) {
            throw new RuntimeException("Only available tables can be deleted");
        }

        if(!user.getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Only admins can delete a table");
        }
        table.setStatus(TableStatus.INACTIVE);
        restaurantTableRepository.save(table);
    }
}
