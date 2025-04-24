package com.oExam.onlineExam.service;

import com.oExam.onlineExam.model.User;
import com.oExam.onlineExam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        return userRepository.save(user);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean verifyPhone(String phone) {
        Optional<User> userOpt = userRepository.findByPhone(phone);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPhoneVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void updateLastLogin(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }


}


