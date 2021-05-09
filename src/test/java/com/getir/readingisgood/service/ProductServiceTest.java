package com.getir.readingisgood.service;


import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.repository.ProductRepository;
import com.getir.readingisgood.utils.Utils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void createProduct_shouldBeSuccess() {
        Product product = Utils.createProduct();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product result = productService.createProduct(product);
        Assert.assertNotNull(result);
    }

    @Test
    public void createProduct_shouldBeError_productNull() {
        expectedException.expect(ReadingIsGoodApiException.class);
        productService.createProduct(null);
    }

    @Test
    public void getProductById_shouldBeSuccess() {
        Product product = Utils.createProduct();
        when(productRepository.findById(product.getId())).thenReturn(java.util.Optional.of(product));
        Product result = productService.getProductById(product.getId());
        Assert.assertNotNull(result);
    }

    @Test
    public void getProductById_shouldBeError_productNotFound() {
        expectedException.expect(ReadingIsGoodApiException.class);
        productService.getProductById(2l);
    }
}
