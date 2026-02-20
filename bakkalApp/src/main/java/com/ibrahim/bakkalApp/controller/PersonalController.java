package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Orders;
import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.OrdersRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonalController {

    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;

    public PersonalController(UserRepository userRepository, OrdersRepository ordersRepository) {
        this.userRepository = userRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null)
            return "redirect:/login";
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("username", userDetails.getUsername());
        }
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null)
            return "redirect:/login";
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "edit-profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails, User updatedUser) {
        if (userDetails == null)
            return "redirect:/login";
        Optional<User> existingUserOpt = userRepository.findByUsername(userDetails.getUsername());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());
            userRepository.save(existingUser);
        }
        return "redirect:/profile";
    }

    @GetMapping("/my-orders")
    public String orders(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null)
            return "redirect:/login";

        Optional<User> userOpt = userRepository.findByUsername(userDetails.getUsername());
        if (userOpt.isPresent()) {
            List<Orders> orders = ordersRepository.findByUserOrderByOrderDateDesc(userOpt.get());
            model.addAttribute("orders", orders);
        }

        model.addAttribute("username", userDetails.getUsername());
        return "orders";
    }
}
