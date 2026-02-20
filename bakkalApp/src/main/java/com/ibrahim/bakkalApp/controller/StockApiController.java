package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.entity.Orders;
import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.OrdersRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import com.ibrahim.bakkalApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Frontend ile iletişim için
public class StockApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {

            System.out.println("Update stock request received: " + payload);
            // CSRF token kontrolü (Spring Security kullanıyorsanız)
            /*
             * String csrfToken = request.getHeader("X-CSRF-TOKEN");
             * if (!isValidCsrfToken(csrfToken)) {
             * return ResponseEntity.status(HttpStatus.FORBIDDEN)
             * .body(Map.of("success", false, "message", "Geçersiz CSRF token"));
             * }
             */

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cart = (List<Map<String, Object>>) payload.get("cart");

            if (cart == null || cart.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Sepet boş"));
            }

            List<String> errors = new ArrayList<>();

            for (Map<String, Object> item : cart) {

                try {
                    Long productId = Long.valueOf(item.get("id").toString());
                    int quantity = Integer.parseInt(item.get("quantity").toString());

                    // Ürünü bul ve stoktan düş
                    Product product = productService.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));

                    if (product == null) {
                        errors.add("Ürün bulunamadı: ID " + productId);
                        continue;
                    }
                    if (product.getStockQuantity() < quantity) {
                        // Yetersiz stok durumu
                        errors.add(product.getName() + " ürününden sadece " +
                                product.getStockQuantity() + " adet kalmıştır");
                    }
                } catch (Exception e) {
                    errors.add("Ürün verisi işlenirken hata: " + e.getMessage());
                }
            }

            // Hata varsa işlemi durdur
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "errors", errors));
            }

            // Stokları güncelle ve toplam tutarı hesapla
            double totalAmount = 0;
            for (Map<String, Object> item : cart) {
                Long productId = Long.valueOf(item.get("id").toString());
                int quantity = Integer.parseInt(item.get("quantity").toString());
                Product product = productService.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));
                product.setStockQuantity(product.getStockQuantity() - quantity);
                productService.save(product);
                totalAmount += product.getPrice() * quantity;
                System.out.println("Stock updated for product " + productId +
                        ", new quantity: " + product.getStockQuantity());
            }

            // Siparişi kaydet
            if (userDetails != null) {
                Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
                if (user.isPresent()) {
                    Orders order = new Orders();
                    order.setUser(user.get());
                    order.setOrderDate(new Date());
                    order.setTotalAmount(totalAmount);
                    ordersRepository.save(order);
                    System.out.println("Order saved for user: " + userDetails.getUsername());
                }
            }

            return ResponseEntity
                    .ok(Map.of("success", true, "message", "Siparişiniz başarıyla alındı ve stoklar güncellendi"));

        } catch (Exception e) {
            System.err.println("Stock update error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Stok güncelleme hatası: " + e.getMessage()));
        }
    }

    @GetMapping("/products/stock")
    public ResponseEntity<List<ProductStockDTO>> getAllProductsStock() {
        try {
            List<Product> products = productService.findAll();
            List<ProductStockDTO> stockList = products.stream()
                    .map(product -> new ProductStockDTO(product.getId(), product.getStockQuantity()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(stockList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Belirli bir ürünün stok durumunu getir
    @GetMapping("/products/{id}/stock")
    public ResponseEntity<ProductStockDTO> getProductStock(@PathVariable Long id) {
        try {
            Product product = productService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + id));
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            ProductStockDTO stockDTO = new ProductStockDTO(product.getId(), product.getStockQuantity());
            return ResponseEntity.ok(stockDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Stok DTO sınıfı
    public static class ProductStockDTO {
        private Long id;
        private int stockQuantity;

        public ProductStockDTO() {
        }

        public ProductStockDTO(Long id, int stockQuantity) {
            this.id = id;
            this.stockQuantity = stockQuantity;
        }

        // Getter ve Setter metodları
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getStockQuantity() {
            return stockQuantity;
        }

        public void setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
        }
    }
}