package com.getir.readingisgood.service;


import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.OrderDetail;
import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.exception.ErrorCodeEnum;
import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.model.CreateOrderRequest;
import com.getir.readingisgood.model.OrderProducts;
import com.getir.readingisgood.model.UpdateProductQuantityRequest;
import com.getir.readingisgood.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final CustomerService customerService;


    @Transactional
    public OrderDetail createNewOrder(CreateOrderRequest request) {
        log.info("******** Incoming OrderCreateRequest Start ********");
        log.info("createNewOrder-start {}", kv("order", request));
        OrderDetail detail = new OrderDetail();
        List<Product> productList = new ArrayList<>();
        Double totalPrice = 0.0;
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        if (!CollectionUtils.isEmpty(request.getProducts())) {
            for (OrderProducts product : request.getProducts()) {
                Product dbProduct = productService.getProductById(product.getProductId());
                totalPrice += calculatePrice(product, dbProduct.getPrice());
                productList.add(dbProduct);
                updateStockRecords(product);
                dbProduct.setQuantity(product.getQuantity());
            }
            detail.setCustomerId(customer.getId());
            detail.setTotalPrice(totalPrice);
            detail.setProducts(productList);
            orderDetailRepository.save(detail);

            log.info("******** Incoming OrderCreateRequest End ********");
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.PRODUCT_NOT_FOUND);
        }
        return detail;
    }


    private Double calculatePrice(OrderProducts product, Double price) {
        return product.getQuantity() * price;
    }

    private void updateStockRecords(OrderProducts product) {
        UpdateProductQuantityRequest request = new UpdateProductQuantityRequest();
        request.setId(product.getProductId());
        request.setQuantity(product.getQuantity());
        productService.updateProductQuantity(request);

    }


    public OrderDetail getCustomerOrder(Long customerId) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findByCustomerId(customerId);
        if (orderDetail != null && orderDetail.isPresent()) {
            return orderDetail.get();
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.ORDER_NOT_FOUND);
        }
    }
}
