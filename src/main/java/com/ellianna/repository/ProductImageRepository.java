package com.ellianna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ellianna.model.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{

    
} 
