package com.getir.readingisgood.controller;


import com.getir.readingisgood.entity.OrderDetail;
import com.getir.readingisgood.model.CreateOrderRequest;
import com.getir.readingisgood.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderService;

    @PostMapping("newOrder")
    public ResponseEntity<List<OrderDetail>> createNewOrder(@Valid @RequestBody CreateOrderRequest request) {
        return new ResponseEntity(orderService.createNewOrder(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<OrderDetail> getCustomerOrders(@RequestParam Long customerId) {
        return ResponseEntity.ok(orderService.getCustomerOrder(customerId));
    }
}
