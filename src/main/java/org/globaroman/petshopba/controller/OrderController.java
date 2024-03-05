package org.globaroman.petshopba.controller;

import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

}