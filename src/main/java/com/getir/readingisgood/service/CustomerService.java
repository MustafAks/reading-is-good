package com.getir.readingisgood.service;


import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.exception.ErrorCodeEnum;
import com.getir.readingisgood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    @Transactional
    public Customer createCustomer(Customer customer) {
        if(customer == null){
            throw new ReadingIsGoodApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
        log.info("******** Incoming Customer Create Start ********");
        log.info("createCustomer-start {}", kv("Customer", customer));
        log.info("******** Incoming  Customer Create End ********");
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        log.info("******** Incoming  getCustomerById Start ********");
        Optional<Customer> dbCustomerEntity = customerRepository.findById(id);
        if (dbCustomerEntity.isPresent()) {
            log.info("******** Incoming getCustomerById End ********");
            return dbCustomerEntity.get();
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
        }
    }

    public Page<Customer> getAllCustomer(int pageIndex, int pageSize) {
        log.info("******** Incoming  getAllCustomer Start ********");
        log.info("getAllEvents {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));
        Page<Customer> response = customerRepository.findAll(PageRequest.of(pageIndex, pageSize));
        log.info("******** Incoming  getAllCustomer End ********");
        return response;
    }
}
