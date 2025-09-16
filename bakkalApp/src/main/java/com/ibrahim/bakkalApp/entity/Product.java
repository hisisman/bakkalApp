package com.ibrahim.bakkalApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private double price;
    @Column(name = "stock_quantity")
    @Min(value = 0, message = "Stok miktarı 0'dan küçük olamaz")
	private int stockQuantity;
    private String imageUrl; // ürün resmi için yeni alan

    // Stok azaltma metodu - güvenli versiyon
    public boolean decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Azaltılacak miktar 0'dan büyük olmalıdır");
        }

        if (this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    // Stok artırma metodu
    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Artırılacak miktar 0'dan büyük olmalıdır");
        }
        this.stockQuantity += quantity;
    }

    // Stok kontrol metodu
    public boolean hasSufficientStock(int requestedQuantity) {
        return this.stockQuantity >= requestedQuantity && requestedQuantity > 0;
    }
}