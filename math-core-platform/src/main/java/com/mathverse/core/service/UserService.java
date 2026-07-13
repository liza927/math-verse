package com.mathverse.core.service;

import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User register(String email,String password) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email найден!");
        } else {
            User newUser = new User();
            newUser.setEmail(email);

            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(Role.STUDENT);
            return userRepository.save(newUser);
        }
    }


    public User login(String email, String password){
        Optional<User> foundUser = userRepository.findByEmail(email);
        if(!foundUser.isPresent()){
            throw new RuntimeException("Неверный логин или пароль");
        }
        User user = foundUser.get();
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Неверный логин или пароль");
        return user;
    }
}
