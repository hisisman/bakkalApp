package com.ibrahim.bakkalApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Base64;

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
    //@NotBlank(message = "Barkod zorunludur")
    private String barcode;

    @Lob
    private byte[] image;

    private String mimeType;

    @Transient
    private String imageBase64;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    // Getter ve Setter metodları
    public String getImageBase64() {
        if (this.image != null && this.mimeType != null) {
            return Base64.getEncoder().encodeToString(this.image);
        }
        return null;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }


    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
}