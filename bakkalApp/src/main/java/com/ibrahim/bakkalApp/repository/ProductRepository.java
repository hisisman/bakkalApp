package com.ibrahim.bakkalApp.repository;

import com.ibrahim.bakkalApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockQuantityGreaterThan(int stockQuantityIsGreaterThan);
}