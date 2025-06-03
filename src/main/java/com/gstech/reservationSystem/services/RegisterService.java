package com.gstech.reservationSystem.services;

import com.gstech.reservationSystem.DTO.UserRegistrationDTO;
import com.gstech.reservationSystem.orm.User;
import com.gstech.reservationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(UserRegistrationDTO data) {

        if (this.userRepository.findByEmail(data.email()).isEmpty()) {

            String encryptPassword = passwordEncoder.encode(data.password());
            userRepository.save(new User(data, encryptPassword));

            return true;

        } return false;
    }
}
