package com.ecommerce.demo_app.Services;


import com.ecommerce.demo_app.Models.Auth;
import com.ecommerce.demo_app.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(Auth auth) {
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));
        authRepository.save(auth);
    }

    public Auth findByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    public boolean checkPassword(Auth auth, String rawPassword) {
        return passwordEncoder.matches(rawPassword, auth.getPassword());
    }
}
