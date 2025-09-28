package com.ibrahim.bakkalApp.service;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

    public List<Product> findAllByIds(Set<Long> longs) {
        return productRepository.findAllById(longs);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        // Validation kontrolü
        if (product.getStockQuantity() < 0) {
            logger.error("Stok miktarı negatif olamaz! Ürün ID: {}", product.getId());
            throw new IllegalArgumentException("Stok miktarı negatif olamaz");
        }
        productRepository.save(product);
        //productRepository.flush();

    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByStockQuantityGreaterThan(int stockQuantity) {
        return productRepository.findByStockQuantityGreaterThan(stockQuantity);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    //public List<Product> findByStockQuantityGreaterThan(int i) {
      //  return
    //}


}