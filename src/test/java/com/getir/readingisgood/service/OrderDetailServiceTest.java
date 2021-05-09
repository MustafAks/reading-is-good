package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.OrderDetail;
import com.getir.readingisgood.entity.Product;
import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.model.CreateOrderRequest;
import com.getir.readingisgood.repository.OrderDetailRepository;
import com.getir.readingisgood.utils.Utils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailServiceTest {

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Mock
    CustomerService customerService;

    @Mock
    ProductService productService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void createNewOrder_shouldBeSuccess() {
        Customer customer = Utils.createCustomer();
        Product product = Utils.createProduct();
        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        when(productService.getProductById(product.getId())).thenReturn(product);
        OrderDetail result = orderDetailService.createNewOrder(Utils.createOrderRequest());
        Assert.assertNotNull(result);
    }

    @Test
    public void createNewOrder_shouldBeError_productEmpty() {
        expectedException.expect(ReadingIsGoodApiException.class);
        Customer customer = Utils.createCustomer();
        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);
        CreateOrderRequest request = Utils.createOrderRequest();
        request.setProducts(null);
        orderDetailService.createNewOrder(request);

    }

    @Test
    public void getCustomerOrder_shouldBeSuccess() {
        Customer customer = Utils.createCustomer();
        when(orderDetailRepository.findByCustomerId(customer.getId())).thenReturn(java.util.Optional.of(Utils.createOrderDetail()));
        OrderDetail result = orderDetailService.getCustomerOrder(customer.getId());
        Assert.assertNotNull(result);
    }

    @Test
    public void getCustomerOrder_shouldBeError_orderNotFound() {
        expectedException.expect(ReadingIsGoodApiException.class);
        Customer customer = Utils.createCustomer();
        when(orderDetailRepository.findByCustomerId(customer.getId())).thenReturn(null);
        orderDetailService.getCustomerOrder(customer.getId());
    }

}
