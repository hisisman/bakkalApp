package com.ibrahim.bakkalApp.config;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, ProductRepository productRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Create Admin if not exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setName("Sistem Yöneticisi");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@bakkalapp.com");
                admin.setPhoneNumber("0000000000");
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Varsayılan Admin hesabı oluşturuldu: admin / admin123");
            }

            // Create some products if not exists
            if (productRepository.count() == 0) {
                // Manav
                Product p1 = new Product();
                p1.setName("Organik Domates");
                p1.setDescription("Dalından taze koparılmış organik tarla domatesi.");
                p1.setPrice(45.0);
                p1.setStockQuantity(50);
                p1.setBarcode("8690000001");
                p1.setCategory("Manav");

                Product p2 = new Product();
                p2.setName("Muz İthal");
                p2.setDescription("Taze ithal muz, kg fiyatı.");
                p2.setPrice(65.0);
                p2.setStockQuantity(40);
                p2.setBarcode("8690000004");
                p2.setCategory("Manav");

                // Süt & Kahvaltılık
                Product p3 = new Product();
                p3.setName("Köy Yumurtası");
                p3.setDescription("Serbest gezen tavuk yumurtası, 10'lu paket.");
                p3.setPrice(85.0);
                p3.setStockQuantity(30);
                p3.setBarcode("8690000002");
                p3.setCategory("Süt & Kahvaltılık");

                Product p4 = new Product();
                p4.setName("Tam Yağlı Süt");
                p4.setDescription("Günlük taze çiftlik sütü, 1 Litre.");
                p4.setPrice(32.50);
                p4.setStockQuantity(100);
                p4.setBarcode("8690000003");
                p4.setCategory("Süt & Kahvaltılık");

                Product p5 = new Product();
                p5.setName("Ezine Peyniri");
                p5.setDescription("Gerçek Çanakkale Ezine peyniri, 500g.");
                p5.setPrice(145.0);
                p5.setStockQuantity(20);
                p5.setBarcode("8690000005");
                p5.setCategory("Süt & Kahvaltılık");

                // Et & Tavuk
                Product p6 = new Product();
                p6.setName("Dana Kıyma");
                p6.setDescription("%20 yağlı taze çekilmiş dana kıyma, 500g.");
                p6.setPrice(220.0);
                p6.setStockQuantity(15);
                p6.setBarcode("8690000006");
                p6.setCategory("Et & Tavuk");

                Product p7 = new Product();
                p7.setName("Tavuk Göğsü");
                p7.setDescription("Taze tavuk göğsü fileto, 1kg.");
                p7.setPrice(160.0);
                p7.setStockQuantity(25);
                p7.setBarcode("8690000007");
                p7.setCategory("Et & Tavuk");

                // Atıştırmalık
                Product p8 = new Product();
                p8.setName("Fındık Ezmesi");
                p8.setDescription("Şeker ilavesiz doğal fındık ezmesi.");
                p8.setPrice(110.0);
                p8.setStockQuantity(60);
                p8.setBarcode("8690000008");
                p8.setCategory("Atıştırmalık");

                Product p9 = new Product();
                p9.setName("Kuru Üzüm");
                p9.setDescription("Manisa çekirdeksiz kuru üzüm, 250g.");
                p9.setPrice(45.0);
                p9.setStockQuantity(80);
                p9.setBarcode("8690000009");
                p9.setCategory("Atıştırmalık");

                // İçecek
                Product p10 = new Product();
                p10.setName("Taze Sıkılmış Portakal Suyu");
                p10.setDescription("Günlük sıkılmış katkısız portakal suyu, 1L.");
                p10.setPrice(75.0);
                p10.setStockQuantity(40);
                p10.setBarcode("8690000010");
                p10.setCategory("İçecek");

                Product p11 = new Product();
                p11.setName("Maden Suyu");
                p11.setDescription("Doğal mineralli maden suyu, 6'lı paket.");
                p11.setPrice(35.0);
                p11.setStockQuantity(120);
                p11.setBarcode("8690000011");
                p11.setCategory("İçecek");

                Product p12 = new Product();
                p12.setName("Sıvı Sabun");
                p12.setDescription("Zeytinyağlı doğal sıvı sabun, 500ml.");
                p12.setPrice(42.50);
                p12.setStockQuantity(45);
                p12.setBarcode("8690000012");
                p12.setCategory("Kişisel Bakım");

                Product p13 = new Product();
                p13.setName("Diş Macunu");
                p13.setDescription("Beyazlatıcı etkili nane özlü diş macunu.");
                p13.setPrice(68.0);
                p13.setStockQuantity(30);
                p13.setBarcode("8690000013");
                p13.setCategory("Kişisel Bakım");

                Product p14 = new Product();
                p14.setName("Çamaşır Deterjanı");
                p14.setDescription("Renkliler için sıvı deterjan, 1.5L.");
                p14.setPrice(185.0);
                p14.setStockQuantity(20);
                p14.setBarcode("8690000014");
                p14.setCategory("Temizlik");

                Product p15 = new Product();
                p15.setName("Yüzey Temizleyici");
                p15.setDescription("Lavanta kokulu genel yüzey temizleyici, 1L.");
                p15.setPrice(38.0);
                p15.setStockQuantity(50);
                p15.setBarcode("8690000015");
                p15.setCategory("Temizlik");

                Product p16 = new Product();
                p16.setName("Taze Simit");
                p16.setDescription("Sıcak, susamlı çıtır sokak simidi.");
                p16.setPrice(12.50);
                p16.setStockQuantity(100);
                p16.setBarcode("8690000016");
                p16.setCategory("Fırın");

                Product p17 = new Product();
                p17.setName("Tam Buğday Ekmeği");
                p17.setDescription("Doyurucu ve sağlıklı tam buğday ekmeği.");
                p17.setPrice(15.0);
                p17.setStockQuantity(40);
                p17.setBarcode("8690000017");
                p17.setCategory("Fırın");

                productRepository
                        .saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17));
                System.out.println("Genişletilmiş örnek ürünler eklendi.");
            }
        };
    }
}
