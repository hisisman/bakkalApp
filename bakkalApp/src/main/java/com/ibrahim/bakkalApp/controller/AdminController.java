package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Orders;
import java.util.List;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import com.ibrahim.bakkalApp.repository.OrdersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;

    public AdminController(ProductRepository productRepository, UserRepository userRepository,
            OrdersRepository ordersRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("orderCount", ordersRepository.count());

        List<Orders> allOrders = ordersRepository.findAll();
        double totalRevenue = allOrders.stream().mapToDouble(Orders::getTotalAmount).sum();
        model.addAttribute("totalRevenue", totalRevenue);

        // Fetch last 5 orders
        List<Orders> recentOrders = allOrders.stream()
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                .limit(5)
                .toList();
        model.addAttribute("recentOrders", recentOrders);

        return "admin/dashboard";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", ordersRepository.findAll());
        return "admin/orders";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

}