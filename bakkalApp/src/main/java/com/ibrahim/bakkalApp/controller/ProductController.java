package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

    @GetMapping("/api")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}

	@PostMapping
	public Product saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla kaydedildi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün kaydedilirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> existingProduct = Optional.ofNullable(productService.getProductById(id));
            if (existingProduct.isPresent()) {
                Product updatedProduct = existingProduct.get();
                updatedProduct.setName(product.getName());
                updatedProduct.setBarcode(product.getBarcode());
                updatedProduct.setPrice(product.getPrice());
                updatedProduct.setStockQuantity(product.getStockQuantity());
                updatedProduct.setDescription(product.getDescription());

                productService.saveProduct(updatedProduct);
                redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla güncellendi!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün güncellenirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ürün başarıyla silindi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ürün silinirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

    @GetMapping
    public String productManagement(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("product", new Product());
        return "admin/product-management";
    }
}