package com.ibrahim.bakkalApp.service;

import com.ibrahim.bakkalApp.entity.Orders;
import com.ibrahim.bakkalApp.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository orderRepository;

	public List<Orders> getAllOrders() {
		return orderRepository.findAll();
	}

	public Orders getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public Orders saveOrder(Orders order) {
		return orderRepository.save(order);
	}

	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}
}