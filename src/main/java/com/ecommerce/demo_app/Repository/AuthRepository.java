package com.ecommerce.demo_app.Repository;


import com.ecommerce.demo_app.Models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByEmail(String email);
}

