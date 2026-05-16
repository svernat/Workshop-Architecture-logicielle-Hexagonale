package com.sve.workshops.hexagonal.exercice2.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        order.id = UUID.randomUUID().toString();
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable String id, @RequestBody Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.username = order.username;
        existingOrder.status = order.status;

        return orderRepository.save(existingOrder);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        orderRepository.deleteById(id);
    }
}
