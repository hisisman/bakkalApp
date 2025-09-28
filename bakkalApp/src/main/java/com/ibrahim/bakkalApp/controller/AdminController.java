package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product-management")
    public String productManagement(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product-management";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam(required = false) MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        try {
            // Eğer resim yüklenmişse
            if (imageFile != null && !imageFile.isEmpty()) {
                // Dosya boyutu kontrolü (5MB)
                if (imageFile.getSize() > 5 * 1024 * 1024) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Resim boyutu 5MB'dan küçük olmalıdır!");
                    return "redirect:/admin/product-management";
                }

                // Dosya tipi kontrolü
                String contentType = imageFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Sadece resim dosyaları yükleyebilirsiniz!");
                    return "redirect:/admin/product-management";
                }

                // Resmi byte array olarak kaydet
                product.setImage(imageFile.getBytes());
                product.setMimeType(imageFile.getContentType());
            }

            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla eklendi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün eklenirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/admin/product-management";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String barcode,
                                @RequestParam Double price,
                                @RequestParam Integer stockQuantity,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            // Mevcut ürünü veritabanından al
            Product existingProduct = productService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + id));

            // Temel alanları güncelle
            existingProduct.setName(name);
            existingProduct.setBarcode(barcode);
            existingProduct.setPrice(price);
            existingProduct.setStockQuantity(stockQuantity);
            existingProduct.setDescription(description);

            // Eğer yeni resim yüklenmişse
            if (imageFile != null && !imageFile.isEmpty()) {
                // Dosya boyutu kontrolü (5MB)
                if (imageFile.getSize() > 5 * 1024 * 1024) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Resim boyutu 5MB'dan küçük olmalıdır!");
                    return "redirect:/admin/product-management";
                }

                // Dosya tipi kontrolü
                String contentType = imageFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Sadece resim dosyaları yükleyebilirsiniz!");
                    return "redirect:/admin/product-management";
                }

                // Yeni resmi kaydet
                existingProduct.setImage(imageFile.getBytes());
                existingProduct.setMimeType(imageFile.getContentType());
            }
            // Eğer resim yüklenmemişse, mevcut resim korunacak (hiçbir şey yapma)

            productService.save(existingProduct);
            redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla güncellendi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün güncellenirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/admin/product-management";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla silindi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün silinirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/admin/product-management";
    }

    // Controller sınıfına bu methodu ekleyin
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Dosya boyutu çok büyük! Maksimum 10MB dosya yükleyebilirsiniz.");
        return "redirect:/admin/product-management";
    }
}