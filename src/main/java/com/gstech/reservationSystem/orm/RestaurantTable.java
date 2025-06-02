package com.gstech.reservationSystem.orm;

import com.gstech.reservationSystem.enums.TableStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_restaurantTable")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private TableStatus status;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public RestaurantTable() {
    }

    public RestaurantTable(String name, int capacity, TableStatus status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
