package com.getir.readingisgood.controller;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        return new ResponseEntity(customerService.createCustomer(customer), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomer(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return ResponseEntity.ok(customerService.getAllCustomer(pageIndex, pageSize));
    }

}
