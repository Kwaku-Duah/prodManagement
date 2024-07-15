package com.ecommerce.demo_app.Repository;


import com.ecommerce.demo_app.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
