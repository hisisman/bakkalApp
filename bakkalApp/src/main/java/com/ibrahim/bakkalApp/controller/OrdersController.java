package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Orders;
import com.ibrahim.bakkalApp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService orderService;

	@GetMapping
	public List<Orders> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{id}")
	public Orders getOrderById(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}

	@PostMapping
	public Orders saveOrder(@RequestBody Orders order) {
		return orderService.saveOrder(order);
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
	}
}