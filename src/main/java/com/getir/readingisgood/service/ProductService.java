package com.getir.readingisgood.service;


import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.exception.ErrorCodeEnum;
import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.model.UpdateProductQuantityRequest;
import com.getir.readingisgood.repository.ProductRepository;
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
public class ProductService {


    private final ProductRepository productRepository;


    @Transactional
    public Product createProduct(Product product) {
        if(product == null){
            throw new ReadingIsGoodApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
        log.info("******** Incoming ProductCreateRequest Start ********");
        log.info("createProduct-start {}", kv("product", product));
        log.info("******** Incoming ProductCreateRequest End ********");
        return productRepository.save(product);

    }

    @Transactional
    public void updateProductQuantity(UpdateProductQuantityRequest request) {
        log.info("******** Incoming updateProductQuantity Start ********");
        log.info("UpdateProductRequest-start {}", kv("request", request));
        log.info("******** Incoming updateProductQuantity End ********");
        if (request.getId() != null) {
            Product dbProduct = this.getProductById(request.getId());
            Integer differance = dbProduct.getQuantity() - request.getQuantity();
            if (differance > 0) {
                dbProduct.setQuantity(differance);
                productRepository.save(dbProduct);
            } else {
                throw new ReadingIsGoodApiException(ErrorCodeEnum.PRODUCT_NOT_AVAIBLE);
            }
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.PRODUCT_ID_MUST_NOT_BLANK);
        }
    }

    public Product getProductById(Long id) {
        log.info("******** Incoming  getProductById Start ********");
        Optional<Product> dbProductEntity = productRepository.findById(id);
        if (dbProductEntity.isPresent()) {
            log.info("******** Incoming getCustomerById End ********");
            return dbProductEntity.get();
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.PRODUCT_NOT_FOUND);
        }
    }

    public Page<Product> getAllProducts(int pageIndex, int pageSize) {
        log.info("******** Incoming  getAllProducts Start ********");
        log.info("getAllEvents {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));
        Page<Product> response = productRepository.findAll(PageRequest.of(pageIndex, pageSize));
        log.info("******** Incoming getAllProducts End ********");
        return response;
    }


}
