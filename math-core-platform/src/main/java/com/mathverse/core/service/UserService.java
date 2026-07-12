package com.mathverse.core.service;

import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String email,String password) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email найден!");
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRole(Role.STUDENT);
            return userRepository.save(newUser);
        }
    }
}
