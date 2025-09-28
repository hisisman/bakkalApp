package com.ibrahim.bakkalApp.repository;

import com.ibrahim.bakkalApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockQuantityGreaterThan(int stockQuantityIsGreaterThan);
    Optional<Product> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
}