package com.ellianna.repository;

import com.ellianna.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findProductByName(String name);
}
